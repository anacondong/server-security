create private key, public key
openssl req -x509 -nodes -days 365 -newkey rsa:2048 -keyout ssl/self-signed.key -out ssl/self-signed.crt


docker-compose up