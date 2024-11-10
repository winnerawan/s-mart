<?php
/**
 */
namespace App\Database\Smart;

/**
 */
class UserPhoto extends AbstractModel
{
	/**
	 */
	protected $table = 'user_photo';
	
	/**
	 */
	protected $primaryKeys = [
		'user_id' => 'user_id'
	];

	/**
	 */
	protected $media;

	/**
	 */
	public function media(){
		return Media::where([
			'media.user_id' => $this->media_user_id,
			'media.uid' => $this->media_uid
		]);
	}

	/**
	 */
	public function getMedia(){
		if ($this->media == null) {
			$this->media = $this->media()->first();
		}
		return $this->media;
	}
}