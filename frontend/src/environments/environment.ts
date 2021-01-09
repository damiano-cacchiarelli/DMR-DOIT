// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,

  autenticazioneURL: 'http://localhost:8080/visitatore',
  iscrittoURL: 'http://localhost:8080/iscritto', 
  progettoURL: 'http://localhost:8080/progetto',
  proponenteURL: 'http://localhost:8080/proponente',
  tagURL: 'http://localhost:8080/tag',
  invitoURL: 'http://localhost:8080/invito', 
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
