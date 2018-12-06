unsigned char *check_passwd(char *plaintext) {
ctext = simple_hash("sha1",plaintext,strlen(plaintext), ... );
//Login if hash matches stored hash
if (equal(ctext, secret_password())) {
login_user();
}
}