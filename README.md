# k-cards-online
An online version of the facilitation tool K-Cards

The latest version of this app can be found in the releases section of the repo and can be started by running:

`java -jar CURRENT_API_NAME`

## Developing k-cards-online

KCO uses:

* NodeJS 12.18.2 for the front-end
* Java 14 for the back-end

### Frontend development

The frontend uses Yarn for development with the following options:

* `yarn start` - Loads up development server (no backend)
* `yarn test` - Runs KCO front-end tests
* `yarn build` - Creates a bundled JS asset for the backend to pull in and use

### Backend development

Backend uses Maven for project management meaning a `mvn clean install` will build the backend.

*Note:* You will need to run `yarn build` in the front-end so that Maven can pull in the front end assets during the build process.
