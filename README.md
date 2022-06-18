# google-oauth-testapp

Very simple java web application for google oauth flow. It gets the basic flow working and code needs to be improved.  

## Auth Flow 
  1. Call Google Auth URL with Client Id, Scope and Redirect URL in the Query Parameter.<br/>
  2. You will be taken to Google login page to enter username/password after which you are redirected to the redirect url.<br/>
  3. In tne redirect url page, pull the authentication code from the redirect request.<br/>
  4. Call Google Token URL with the Authentication Code, Client Id, Client Secret, Grant Type and Redirect URL in POST parameters.<br/>
  5. Google reponds back with the access token in json response.<br/>
  6. Call the Google APIs with the access token as (Authorization : Bearer Access_Token) in the header. <br/>
  7. Google will respond back with API json response.<br/>
 
### More relevant links 

https://jenkov.com/tutorials/oauth2/index.html<br/>
https://www.oauth.com/oauth2-servers/background/<br/>
https://developer.okta.com/docs/concepts/oauth-openid/#client-credentials-flow<br/>
https://developers.google.com/identity/protocols/oauth2<br/>


