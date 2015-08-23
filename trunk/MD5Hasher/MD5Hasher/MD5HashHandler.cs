/*
 * 
 * User: Faizul
 * Date: 12/31/2005
 * Time: 12:27 PM
 * 
 * Updates:
 * 2006/01/17 : introduce UseUpperCase properties so that the MD5 output is in 
 *              upper case if assigned to true  
 */

using System;
using System.IO;
using System.Security.Cryptography;

namespace Hasher
{
	/// <summary>
	/// Description of MD5HashHandler.
	/// </summary>
	public class MD5HashHandler
	{
		private MD5 md5;
		private bool useUpperCase;
		
		public MD5HashHandler()
		{
			md5 = new MD5CryptoServiceProvider();
			useUpperCase = false;
		}
		
		public bool TestHashFromFile(string flname, string hashValue) 
		{
			string mHashOutput;
			mHashOutput = CalculateHashFromFile(flname);
			if(useUpperCase) return hashValue.ToUpper().Equals(mHashOutput);
			else return hashValue.ToLower().Equals(mHashOutput);
		}
		
		public string CalculateHashFromFile(string filename) 
		{
			if(System.IO.File.Exists(filename)) {
				FileStream fs = File.OpenRead(filename);
				fs.Lock(0, fs.Length);
					byte[] result = md5.ComputeHash(fs);
				fs.Unlock(0,fs.Length);
				fs.Close();
				if(useUpperCase) return ToHexString(result).ToUpper();
				else return ToHexString(result).ToLower();
			}
			return string.Empty;
		}
		
		public bool UseUpperCase 
		{
			get {
				return useUpperCase;
			}
			
			set {
				useUpperCase = value;
			}
		}
//rip-off from some textbook.. forgot the author's name....	
    	protected string ToHexString(byte[] bytes) {
			char[] hexDigits = {
        			'0', '1', '2', '3', '4', '5', '6', '7',
        			'8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
			};
        	
			char[] chars = new char[bytes.Length * 2];
        	for (int i = 0; i < bytes.Length; i++) {
            	int b = bytes[i];
            	chars[i * 2] = hexDigits[b >> 4];
            	chars[i * 2 + 1] = hexDigits[b & 0xF];
        	}
        	return new string(chars);
    	}
	
	}
}
