<?php
/**
 */
namespace App\Service\Api\Auth;

use Exception;
use RuntimeException;
use Lcobucci\JWT\Parser;
use Lcobucci\JWT\Builder;
use Lcobucci\JWT\ValidationData;
use Lcobucci\JWT\Signer\Key;
use Lcobucci\JWT\Signer\Rsa\Sha256;
use App\Database\Smart;
use App\Service\Api\Config;

/**
 */
abstract class TokenJwt
{
	/**
	 */
	protected static $signer;
	protected static $publicKey;
	protected static $privateKey;

	/**
	 */
	public static function getSigner(){
		if (static::$signer === null) {
			static::$signer = new Sha256();
		}
		return static::$signer;
	}

	/**
	 */
	public static function getPublicKey(){
		if (static::$publicKey !== null) {
			return static::$publicKey;
		}
		$publicKeyFile = Config::get('auth.jwt.public_key');
		if (!$publicKeyFile || !is_file($publicKeyFile)) {
			throw new RuntimeException('The public key file not found.');
		}
		$publicKeyPassphrase = Config::get('auth.jwt.public_key_passphrase');
		if (!$publicKeyPassphrase) {
			throw new RuntimeException('The public key passphrase is empty.');
		}
		static::$publicKey = new Key(sprintf('file://%s',$publicKeyFile), $publicKeyPassphrase);
		return static::$publicKey;
	}

	/**
	 */
	public static function getPrivateKey(){
		if (static::$privateKey !== null) {
			return static::$privateKey;
		}
		$privateKeyFile = Config::get('auth.jwt.private_key');
		if (!$privateKeyFile || !is_file($privateKeyFile)) {
			throw new RuntimeException('The private key file not found.');
		}
		$privateKeyPassphrase = Config::get('auth.jwt.private_key_passphrase');
		if (!$privateKeyPassphrase) {
			throw new RuntimeException('The private key passphrase is empty.');
		}
		static::$privateKey = new Key(sprintf('file://%s', $privateKeyFile), $privateKeyPassphrase);
		return static::$privateKey;
	}

	/**
	 */
	public static function parseToken($token){
		$data = new ValidationData();
		$data->setSubject(Smart\TokenType::TYPE_BEARER_ID);
		try {
			$jwt = (new Parser())->parse($token);
			if ($jwt->verify(static::getSigner(), static::getPublicKey()) && $jwt->validate($data)) {
				return TokenModel::fromJwt($jwt);
			}
		} catch (Exception $e) {}
	}
	
	/**
	 */
	public static function buildToken(TokenModel $model){
		$builder = new Builder();
		$builder->setId($model->getUnique(), true);
		$builder->setIssuedAt($model->getCreated()->getTimestamp());
		$builder->setNotBefore($model->getCreated()->getTimestamp());
		$builder->setExpiration($model->getExpired()->getTimestamp());
		$builder->setAudience($model->getUserId());
		$builder->setSubject($model->getTokenTypeId());
		$builder->sign(static::getSigner(), static::getPrivateKey());
		return $builder->getToken();
	}
}