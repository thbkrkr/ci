#!/bin/bash -eu

source bin/generate-jobs-dsl.sh

generate_all_jobs() {
  gen_build_master_github_job() {
    declare name=$1
    gen build_tag_push $name master \
      https://github.com/$github_username/$name https://github.com/$github_username/$name \
      "public"
  }
  gen_build_master_github_job docker-toolbox
  gen_build_master_github_job jks
  gen_build_master_github_job sysd
  gen_build_master_github_job logz
  gen_build_master_github_job toctoc

  gen build_make sh-bot master \
    https://github.com/thbkrkr/qli/sh-bot https://github.com/thbkrkr/qli "public" \
    "make -C sh-bot build"

  gen build_make c1 master \
    https://bitbucket.org/thb/c1 "git@bitbucket.org:thb/c1.git" "ci-bitbucket.id_rsa" \
    "doo bp"

  gen deploy_make c1 master \
    https://bitbucket.org/thb/c1 "git@bitbucket.org:thb/c1.git" "ci-bitbucket.id_rsa" \
    "make deploy" \
    'build_c1,build_toctoc,build_functions,build_sh-bot,build_docker-toolbox,build_dops,build_jks,build_logz'

  gen build_make dops master \
    https://github.com/thbkrkr/dops "https://github.com/thbkrkr/dops" "public" \
    "make build"

  gen build_make functions master \
    https://github.com/thbkrkr/functions https://github.com/thbkrkr/functions "public" \
    "make deploy"
}

github_username=thbkrkr

public_github_repos() {
echo '
  ci
  functions
  qli
  docker-toolbox
  dops
  jks
  logz
  toctoc
  sysd
'
}

main() {
  case ${1:-gen_jobs} in
    gen_jobs)
      generate_all_jobs
    ;;
    webhooks)
      bin/github-webhooks.sh sync_all "$(public_github_repos)" $github_username
    ;;
    *)
      echo "Unknown parameter."
    ;;
  esac
}

main "$@"