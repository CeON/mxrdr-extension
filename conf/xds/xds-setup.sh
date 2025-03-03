#!/bin/bash

set -e

SCRIPT_DIR="$( cd -- "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"
INSTALL_DIR="/opt/xds"
BACKUP_DIR="/opt/xds_backup"
XDS_TAR_DOWNLOAD_URL=https://xds.mr.mpg.de/XDS-INTEL64_Linux_x86_64.tar.gz
XDS_TAR=$(basename ${XDS_TAR_DOWNLOAD_URL})
GENERATE_XDS_INP_DOWNLOAD_URL=https://wiki.uni-konstanz.de/pub/linux_bin/generate_XDS.INP
GENERATE_XDS_INP=$(basename $GENERATE_XDS_INP_DOWNLOAD_URL)

DATAVERSE_API_HTTP="http://localhost:8080/api"
WORKFLOW_JSON_FILE="workflow.json"
METADATA_FILE="macromolecularcrystallography.tsv"

(

  usage() {
    echo "Usage: $(basename $0) [command]"
    echo "Commands: "
    echo " install_all                Fresh installation of all components: XDS binaries, XDS workflow and metadata import."
    echo " install_xds_bin            Install the latest XDS binaries"
    echo " install_xds_workflow       Install xds analysis workflow"
    echo " import_metadata            Import macro molecular crystallography metadata"
    echo " show                       Display configuration"
    echo " -h|help"
    exit
  }

  install_all() {
    install_xds_bin
    install_xds_workflow
    import_metadata
  }

  install_xds_bin() {
    (
      echo "Installing XDS binaries"
      if [ -d ${INSTALL_DIR} ]; then
        echo "Installation directory ${INSTALL_DIR} exists. Backing it up to $BACKUP_DIR"
        if [ -d ${BACKUP_DIR} ]; then
          echo "Cleaning existing backup directory ${BACKUP_DIR}"
          rm -r ${BACKUP_DIR}
        fi
        mv $INSTALL_DIR $BACKUP_DIR
      fi

      mkdir -p ${INSTALL_DIR}
      cd ${INSTALL_DIR}

      wget --no-check-certificate ${XDS_TAR_DOWNLOAD_URL} && echo "Downloaded newest XDS binaries"
      tar -xzf "${XDS_TAR}" --strip-components=1
      rm -f "${XDS_TAR}"
      wget --no-check-certificate ${GENERATE_XDS_INP_DOWNLOAD_URL} && echo "Downloaded generate_XDS.INP"
      chmod a+x "${GENERATE_XDS_INP}"

      if ! [[ "$PATH" =~ "$INSTALL_DIR:" ]]
      then
          {
            echo ""
            echo "export PATH=\"$INSTALL_DIR:\$PATH\""
            echo ""
          } >> ~/.bashrc
      fi
    )

    echo "Done."
  }

  install_xds_workflow() {
    echo "Importing workflow"
    WORKFLOW_ID=$(curl -X POST ${DATAVERSE_API_HTTP}/admin/workflows -H 'Content-Type: application/json' -d @${SCRIPT_DIR}/${WORKFLOW_JSON_FILE} | sed -e 's/.*"id":\([0-9]*\),.*/\1/')
    if ! [[ ${WORKFLOW_ID} =~ ^[0-9]+$ ]] ; then
       echo "Error installing workflow. Response: ${WORKFLOW_ID}";
       exit 1
    fi

    echo "Workflow imported with id:${WORKFLOW_ID}. Setting it now as default workflow for PostPublishDataset."
    curl -X PUT http://localhost:8080/api/admin/workflows/default/PostPublishDataset -d "${WORKFLOW_ID}"

    echo "Done."
  }

  import_metadata() {
    echo "Loading Macro Molecular Crystallography metadata"
    curl -X POST ${DATAVERSE_API_HTTP}/admin/datasetfield/load -H "Content-type: text/tab-separated-values" --upload-file ${SCRIPT_DIR}/${METADATA_FILE}

    echo "Done."
  }

  show() {
    echo "INSTALL_DIR: ${INSTALL_DIR}"
    echo "BACKUP_DIR: ${BACKUP_DIR}"
    echo "XDS_TAR_DOWNLOAD_URL: ${XDS_TAR_DOWNLOAD_URL}"
    echo "XDS_TAR: ${XDS_TAR}"
    echo "GENERATE_XDS_INP_DOWNLOAD_URL: ${GENERATE_XDS_INP_DOWNLOAD_URL}"
    echo "GENERATE_XDS_INP: ${GENERATE_XDS_INP}"
    echo "WORKFLOW_JSON_FILE: ${WORKFLOW_JSON_FILE}"
    echo "METADATA_FILE: ${METADATA_FILE}"
    echo "DATAVERSE_HTTP: ${DATAVERSE_HTTP}"
  }

  if [ $# -eq 0 ]; then
    usage
    exit
  fi


  while [[ $# -gt 0 ]]
  do
    key="$1"
    case $key in
      install_all)
        shift
        install_all
        exit
        ;;
      install_xds_bin)
        shift
        install_xds_bin
        exit
        ;;
      install_xds_workflow)
        shift
        install_xds_workflow
        exit
        ;;
      import_metadata)
        shift
        import_metadata
        exit
        ;;
      show)
        shift
        show
        exit
        ;;
      -h|--help)
        usage
        exit
        ;;
      *)
        usage
        exit
        ;;
    esac
  done
)
