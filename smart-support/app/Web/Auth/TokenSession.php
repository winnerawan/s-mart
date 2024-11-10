<?php
/**
 */
namespace App\Web\Auth;

use RuntimeException;
use Carbon\Carbon;
use Illuminate\Support\Facades\Cookie;
use App\Web\Config;

/**
 */
abstract class TokenSession
{
	/**
	 */
	protected static $name;
	protected static $domain;
	protected static $secure;
	protected static $path;

	/**
	 */
	public static function getName(){
		if (static::$name !== null) {
			return static::$name;
		}
		static::$name = Config::get('auth.session.name');
		if (static::$name == null) {
			throw new RuntimeException(__METHOD__);
		}
		return static::$name;
	}
	
	/**
	 */
	public static function getDomain(){
		if (static::$domain === null) {
			static::$domain = (string)Config::get('auth.session.domain');
		}
		return static::$domain;
	}

	/**
	 */
	public static function getSecure(){
		if (static::$secure === null) {
			static::$secure = !!Config::get('auth.session.secure');
		}
		return static::$secure;
	}

	/**
	 */
	public static function getPath(){
		if (static::$path === null) {
			static::$path = (string)Config::get('auth.session.path');
		}
		return static::$path;
	}
	
	/**
	 */
	public static function register(TokenModel $model){
		Cookie::queue(static::createCookieQueue($model));
	}
	
	/**
	 */
	public static function unregister(){
		Cookie::queue(static::createCookieForget());
	}

	/**
	 */
	protected static function createCookieQueue(TokenModel $model){
		$token = TokenEncryption::encrypt($model);
		$minutes = $model->getExpired()->diffInMinutes(Carbon::now());
		return Cookie::make(static::getName(), $token, $minutes, static::getPath(), static::getDomain(), static::getSecure());
	}

	/**
	 */
	protected static function createCookieForget(){
		return Cookie::forget(static::getName(), static::getPath(), static::getDomain());
	}
}