<?php
/**
 */
namespace App\Service\Api\User;

use App\Support\Validator\AbstractValidator;

/**
 */
abstract class UserValidator extends AbstractValidator
{
	/**
	 */
	public function isTokenExists(){
		return User::isTokenExists();
	}
	
	/**
	 */
	public function getToken(){
		return User::getToken();
	}
	
	/**
	 */
	public function getUser(){
		return User::getUser();
	}
}