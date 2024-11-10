<?php
/**
 */
namespace App\Service\Api\Auth;

use RuntimeException;

/**
 */
abstract class Token
{
	/**
	 */
	protected static $setup;
	protected static $input;
	protected static $model;
	protected static $token;
	protected static $user;

	/**
	 */
	public static function setup($input){
		if (!!static::$setup) {
			throw new RuntimeException(__METHOD__);
		}
		static::$setup = true;
		static::$input = $input;
		return static::validateInput() && static::validateModel();
	}

	/**
	 */
	public static function getInput(){
		return static::$input;
	}

	/**
	 */
	public static function getModel(){
		return static::$model;
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

	/**
	 */
	protected static function validateInput(){
		static::$model = static::$input != null ? TokenJwt::parseToken(static::$input) : null;
		return !!static::$model && static::$model->validate();
	}

	/**
	 */
	protected static function validateModel(){
		static::$token = static::$model->toToken();
		return !!static::$token && (static::getUser()->getUserStatus()->isStatusActive() || static::getUser()->getUserStatus()->isStatusInactive());
	}
}