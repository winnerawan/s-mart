<?php
/**
 */
namespace App\Database\Smart;

/**
 */
class MediaPathStatus extends AbstractModel
{
	/**
	 */
	const STATUS_ACTIVE_ID = 1;
	const STATUS_REMOVED_ID = 2;
	const STATUS_INACTIVE_ID = 3;
	
	/**
	 */
	protected $table = 'media_path_status';
	
	/**
	 */
	protected $visible = [
		'id',
		'name'
	];
	
	/**
	 */
	public function isStatusOf($id){
		return $this->id == $id;
	}

	/**
	 */
	public function isStatusActive(){
		return $this->isStatusOf(static::STATUS_ACTIVE_ID);
	}

	/**
	 */
	public function isStatusRemoved(){
		return $this->isStatusOf(static::STATUS_REMOVED_ID);
	}

	/**
	 */
	public function isStatusInactive(){
		return $this->isStatusOf(static::STATUS_INACTIVE_ID);
	}
}