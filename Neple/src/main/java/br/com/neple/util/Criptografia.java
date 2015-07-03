package br.com.neple.util;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class Criptografia {
	private static final String ALGORITMO = "AES";
	private static final String CHAVE = "@#N3pl3#1ngl3s#@";

	public static String cifrar(String texto) {
		try {
			Key chave = new SecretKeySpec(CHAVE.getBytes(), ALGORITMO);

			Cipher cifrador = Cipher.getInstance(ALGORITMO);
			cifrador.init(Cipher.ENCRYPT_MODE, chave);

			byte[] bufferTexto = texto.getBytes();
			byte[] bufferTextoCifrado = cifrador.doFinal(bufferTexto);
			byte[] bufferTextoEncodado = Base64
					.encodeBase64(bufferTextoCifrado);

			String textoEncodado = new String(bufferTextoEncodado);

			return textoEncodado;
		} catch (NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidKeyException | IllegalBlockSizeException
				| BadPaddingException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	public static String decifrar(String textoEncodado) {
		try {
			Key chave = new SecretKeySpec(CHAVE.getBytes(), ALGORITMO);

			Cipher cifrador = Cipher.getInstance(ALGORITMO);
			cifrador.init(Cipher.DECRYPT_MODE, chave);

			byte[] bufferTextoEncodado = textoEncodado.getBytes();
			byte[] bufferTextoDesencodado = Base64.decodeBase64(bufferTextoEncodado);
			byte[] bufferTextoDecifrado = cifrador.doFinal(bufferTextoDesencodado);
			
			String texto = new String(bufferTextoDecifrado);

			return texto;
		} catch (NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidKeyException | IllegalBlockSizeException
				| BadPaddingException exception) {
			throw new RuntimeException(exception);
		}
	}
}
