<?php
/**
 */
namespace App\Web\User;

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