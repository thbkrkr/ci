#!/bin/bash -eu
#
# Generate Jenkins jobs groovy DSL scripts.
#
set -o pipefail

HERE=$(dirname "$(readlink -f "$0")")

gen() {
  export kind=$1
  export name=$2
  export branch=$3
  export repo=$4
  export origin=$5
  export credentials=$6
  export make_cmd=${7:-""}
  export upstream=${8:-""}
  case $repo in
    *bitbucket*) gitProvider=bitbucket ;;
    *github*)    gitProvider=github    ;;
    *)           gitProvider=xx        ;;
  esac
  export gitProvider=$gitProvider

  echo gen job kind=$kind name=$name branch=$branch repo=$repo gitProvider=$gitProvider

  DOLLAR='$' envsubst \
      < bin/tpl/job_${kind}.groovy.tpl \
      > jobs/${kind}_$(sed "s|-|_|g" <<< $name)_${branch}.groovy
}

