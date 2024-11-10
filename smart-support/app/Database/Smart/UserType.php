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
	const TYPE_MEMBER_ID = 3;
	const TYPE_SECURITY_ID = 4;
	const TYPE_GUEST_ID = 5;
	const TYPE_MEMBER_NON_PERMANENT_ID = 6;	
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

	/**
	 */
	public function isTypeMember(){
		return $this->isTypeOf(static::TYPE_MEMBER_ID);
	}

	/**
	 */
	public function isTypeSecurity(){
		return $this->isTypeOf(static::TYPE_SECURITY_ID);
	}

	/**
	 */
	public function isTypeMemberNonPermanent(){
		return $this->isTypeOf(static::TYPE_MEMBER_NON_PERMANENT_ID);
	}

}