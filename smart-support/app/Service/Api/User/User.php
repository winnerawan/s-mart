<?php
/**
 */
namespace App\Service\Api\User;

use RuntimeException;
use App\Database\Smart;

/**
 */
abstract class User
{
	/**
	 */
	protected static $setup;
	protected static $token;
	protected static $user;

	/**
	 */
	public static function setup(Smart\Token $token){
		if (!!static::$setup) {
			throw new RuntimeException(__METHOD__);
		}
		static::$setup = true;
		return !!static::$token = $token;
	}
	
	/**
	 */
	public static function isTokenExists(){
		return !!static::$token;
	}
	
	/**
	 */
	public static function getToken(){
		return static::$token;
	}

	/**
	 */
	public static function getUser(){
		if (static::$user === null) {
			static::$user = static::getToken()->getUser();
		}
		return static::$user;
	}
}