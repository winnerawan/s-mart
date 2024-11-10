<?php
/**
 */
namespace App\Database\Smart;

/**
 */
class UserPhone extends AbstractModel
{
	/**
	 */
	protected $table = 'user_phone';
	
	/**
	 */
	protected $primaryKeys = [
		'user_id' => 'user_id'
	];
}