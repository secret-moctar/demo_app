pipeline {
agent any
environment {
ANSIBLE_INVENTORY = 'path/to/your/inventory/file'
ANSIBLE_PLAYBOOK = 'path/to/your/playbook.yml'

}
stages {
stage('Checkout') {
steps {
git branch: 'main', 
url: 'https://github.com/secret-moctar/demo_app'
}
}
stage('Install Ansible') {
steps {
sh 'sudo apt-get update'
sh 'sudo apt-get install -y ansible'
}
}
stage('Run Ansible Playbook') {
steps {
ansiblePlaybook(
playbook: "${ANSIBLE_PLAYBOOK}",
inventory: "${ANSIBLE_INVENTORY}"
)
}
}
}
}