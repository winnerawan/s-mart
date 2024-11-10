<?php
/**
 */
namespace App\Database\Smart;

/**
 */
class UserType extends AbstractModel
{
	/**
	 */
	const TYPE_SYSTEM_ID = 1;
	const TYPE_ADMINISTRATOR_ID = 2;
	/**
	 */
	protected $table = 'user_type';
	
	/**
	 */
	protected $visible = [
		'id',
		'name'
	];
	
	/**
	 */
	public function isTypeOf($id){
		return $this->id == $id;
	}


	/**
	 */
	public function isTypeSystem(){
		return $this->isTypeOf(static::TYPE_SYSTEM_ID);
	}

	/**
	 */
	public function isTypeAdministrator(){
		return $this->isTypeOf(static::TYPE_ADMINISTRATOR_ID);
	}

}