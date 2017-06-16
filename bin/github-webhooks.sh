#!/bin/bash -eu
#
# Manage GitHub webhooks.
#
set -o pipefail

if [[ -z "$GITHUB_TOKEN" ]]; then
  echo "Set the GITHUB_TOKEN env variable."
  exit 1
fi

URI=https://api.github.com
API_VERSION=v3
API_HEADER="Accept: application/vnd.github.${API_VERSION}+json"
AUTH_HEADER="Authorization: token ${GITHUB_TOKEN}"
JENKINS_HOOK_URL="${JENKINS_HOOK_URL:-https://jenkins.blurb.space/github-webhook/}"

api() {
  declare uri=${1} && shift || true
  curl -sSL -H "${AUTH_HEADER}" -H "${API_HEADER}" "${URI}/repos/${repo}${uri}" "$@"
}

create_hook() {
  declare repo=$1
  repo="${repo%%/hooks*}"

  echo "Creating webhook for $repo"
  api "/hooks" -XPOST -d '
  {
    "name": "jenkins",
    "active": true,
    "events": ["push"],
    "config": {
      "url": "'${JENKINS_HOOK_URL}'",
      "jenkins_hook_url": "'${JENKINS_HOOK_URL}'",
      "content_type": "form"
    }
  }'
}

edit_hook() {
  declare repo=$1

  # Retrieve hook id
  hookID=$(api "/hooks" | jq -Mcr '.[] | select(.config.jenkins_hook_url) | .id')

  echo "Editing webhook for $repo"
  api "/hooks/$hookID" -XPATCH -d '
  {
    "name": "jenkins",
    "active": true,
    "events": ["push"],
    "config": {
      "url": "'${JENKINS_HOOK_URL}'",
      "jenkins_hook_url": "'${JENKINS_HOOK_URL}'",
      "content_type": "form"
    }
  }'
}

get_hook() {
  declare repo=$1

  echo "Getting webhook for $repo"
  api "/hooks" | jq -Mcr '.[] | .config.jenkins_hook_url'
}

delete_hook() {
  declare repo=$1

  ID=$(api "/hooks" | jq -Mcr '.[] | select(.name=="jenkins") | .id')

  if [[ "$ID" == "" ]]; then
    echo "Webhook not found for $repo"
  else
    echo "Deleting webhook for $repo"
    api "/hooks/$ID" -XDELETE
  fi
}

purple="\e[35m"
green="\e[32m"
red="\e[31m"
reset="\e[39m"

sync_all() {
  declare public_github_repos="$1" github_username="$2"

  for r in $public_github_repos; do
    repo=$github_username/$r
    hook=$(bin/github-webhooks.sh get $repo | tail -1)

    if [[ "$hook" == "$JENKINS_HOOK_URL" ]]; then
      echo -e "${green}OK${reset} webhook $repo"
    else
      echo -e "${purple}KO${reset} Hook for $repo not found"
      if [[ "$hook" == "" ]]; then
        bin/github-webhooks.sh create $repo $hook
      else
        bin/github-webhooks.sh edit $repo $hook
      fi
    fi
  done
}

main() {
  declare action=$1 repo=$2

  if [[ "$action" == "" ]]; then
    echo "Pass an action as the first argument: ex. create, edit, get or delete" >&2
    exit 1
  fi

  if [[ "$repo" == "" ]]; then
    echo "Pass a repo as the second argument: ex. thbkrkr/bim" >&2
    exit 1
  fi

  case $action in
    create|edit|get|delete)
      ${action}_hook "$2"
      ;;
    sync_all)
      sync_all "$2" "$3"
      ;;
    *)
      echo "Invalid action ${action}. Try: create, edit, get, delete" >&2
      exit 1
      ;;
  esac
}

main "$@"