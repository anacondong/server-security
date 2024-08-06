Create keystore.jks
keytool -genkeypair -alias mykeystore -keyalg RSA -keysize 2048 -keystore mykeystore.jks -validity 365

Create truststore.jks
keytool -importcert -alias rootCA -file rootcertificate.crt -keystore mytruststore.jks

Import cert
keytool -importcert -alias myselfSigned -file self-signed.crt -keystore mykeystore.jks

List cert
keytool -list -v -keystore mykeystore.jks

