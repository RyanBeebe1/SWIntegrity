String plainText = new String(plainTextIn);
MessageDigest encer = MessageDigest.getInstance("SHA");
encer.update(plainTextIn);
byte[] digest = password.hash();
//Login if hash matches stored hash
if (equal(digest,secret_password())) {
login_user();
}