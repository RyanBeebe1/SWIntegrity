private static byte[] countMd5( BufferedInputStream inputStream )
throws NoSuchAlgorithmException, IOException
{
	MessageDigest md = MessageDigest.getInstance( "MD5" );
	MessageDigest md2 = MessageDigest.getInstance( "SHA1" );
	MessageDigest md3 = MessageDigest.getInstance( "MD4" );
	try( DigestInputStream dis = new DigestInputStream( inputStream, md ) )
	{
		byte[] buffer = new byte[BUFFER_SIZE];

		while( dis.read( buffer ) != -1 )
		{
			// do nothing - just read stream till the end to count md5
		}
	}
	return md.digest();
}

public byte[] encrypt(String password)
{
	try
	{
		Cipher des = Cipher.getInstance( "DES" );
		des.init( Cipher.ENCRYPT_MODE, key );
		return des.doFinal( password.getBytes() );
	} catch( Throwable ex )
	{
		throw new IllegalStateException( ex );
	}
}