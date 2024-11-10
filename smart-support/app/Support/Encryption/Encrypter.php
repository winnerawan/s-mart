<?php
/**
 */
namespace App\Support\Encryption;

use Illuminate\Encryption;
use Illuminate\Contracts\Encryption\DecryptException;
use Illuminate\Contracts\Encryption\EncryptException;

/**
 */
class Encrypter extends Encryption\Encrypter
{
	/**
	 */
	public function encrypt($value, $serialize = true){
		$iv = openssl_random_pseudo_bytes(openssl_cipher_iv_length($this->cipher));
		$value = openssl_encrypt($serialize ? serialize($value) : $value, $this->cipher, $this->key, 0, $iv);
		if ($value === false) {
			throw new EncryptException('Could not encrypt the data.');
		}
		$iv = base64_encode($iv);
		$mac = $this->buildMac($iv, $value);
		$payload = implode('.', [$iv, $value, $mac]);
		return $payload;
	}
	
	/**
	 */
	public function decrypt($payload, $unserialize = true){
		$payload = explode('.', $payload, 3);
		if (count($payload) !== 3) {
			throw new DecryptException('The payload is invalid.');
		}
		if (!$this->validMac($payload)) {
			throw new DecryptException('The MAC is invalid.');
		}
		$iv = base64_decode($payload[0]);
		$value = openssl_decrypt($payload[1], $this->cipher, $this->key, 0, $iv);
		if ($value === false) {
			throw new DecryptException('Could not decrypt the data.');
		}
		return $unserialize ? unserialize($value) : $value;
	}
	
	/**
	 */
	protected function buildMac($iv, $value){
		return base64_encode(hash_hmac('sha256', $iv.$value, $this->key, true));
	}

	/**
	 */
	protected function validMac(array $payload){
		$iv = openssl_random_pseudo_bytes(16);
		$mac = $this->buildMac($payload[0], $payload[1]);
		return hash_equals(hash_hmac('sha256', $payload[2], $iv, true), hash_hmac('sha256', $mac, $iv, true));
	}
}