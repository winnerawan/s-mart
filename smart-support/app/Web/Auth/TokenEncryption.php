<?php
/**
 */
namespace App\Web\Auth;

use Exception;
use RuntimeException;
use App\Support\Encryption\Encrypter;
use App\Web\Config;

/**
 */
abstract class TokenEncryption
{
	/**
	 */
	protected static $key;
	protected static $cipher;
	protected static $encrypter;

	/**
	 */
	public static function getKey(){
		if (static::$key !== null) {
			return static::$key;
		}
		$key = Config::get('auth.encryption.key');
		if ($key == null) {
			throw new RuntimeException(__METHOD__);
		}
		return static::$key = base64_decode($key);
	}
	
	/**
	 */
	public static function getCipher(){
		if (static::$cipher !== null) {
			return static::$cipher;
		}
		$cipher = Config::get('auth.encryption.cipher');
		if ($cipher == null) {
			throw new RuntimeException(__METHOD__);
		}
		return static::$cipher = $cipher;
	}
	
	/**
	 */
	public static function getEncrypter(){
		if (static::$encrypter === null) {
			static::$encrypter = new Encrypter(static::getKey(), static::getCipher());
		}
		return static::$encrypter;
	}

	/**
	 */
	public static function encrypt(TokenModel $model){
		try {
			$value = $model->toJson();
			return static::getEncrypter()->encryptString($value);
		} catch (Exception $e) {}
	}

	/**
	 */
	public static function decrypt($value){
		try {
			$value = static::getEncrypter()->decryptString($value);
			return TokenModel::fromJson($value);
		} catch (Exception $e) {}
	}
}