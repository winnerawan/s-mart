<?php
/**
 */
namespace App\Database\Smart;

/**
 */
class TokenType extends AbstractModel
{
	/**
	 */
	const TYPE_BEARER_ID = 1;
	const TYPE_SESSION_ID = 2;
	
	/**
	 */
	protected $table = 'token_type';
	
	/**
	 */
	protected $visible = [
		'id',
		'name'
	];
}