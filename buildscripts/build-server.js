const shell = require('shelljs');

shell.echo('##########################');
shell.echo('#     Building JS        #');
shell.echo('##########################');

shell.cd('csvDuplicateReader-static');
const RESOURCES = '../csvDuplicateReader-svc/src/main/resources';
const PUBLIC = `${RESOURCES}/public`;
shell.rm('-rf', PUBLIC);
if (shell.exec('yarn run deploy').code !== 0) {
  shell.echo('Error: js build failed');
  shell.exit(1);
}
shell.cp('-R', 'build/', PUBLIC);
shell.cp('build/index.html', `${RESOURCES}/templates/main.html`);
shell.cd('..');

shell.echo('##########################');
shell.echo('#     Building Backend   #');
shell.echo('##########################');

shell.cd('csvDuplicateReader-svc');
const mvnw = process.platform === 'win32' ? 'mvnw' : './mvnw';
if (shell.exec(`${mvnw} -Pprod clean package -DskipTests`).code !== 0) {
  shell.echo('Error: spring build failed');
  shell.exit(1);
}
