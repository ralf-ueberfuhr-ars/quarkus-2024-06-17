# JWT Token Generator

Run the main method the generate a token that is encrypted with the private token
that matches the public token in the Customer API provider app.

Then, use the token in the command line:

```bash
curl -H "Authorization: Bearer <token> " http://localhost:8080/secured/roles-allowed; echo
```

**Be aware:** The token will expire, so we have to generate tokens frequently.

See Details at https://quarkus.io/guides/security-jwt.