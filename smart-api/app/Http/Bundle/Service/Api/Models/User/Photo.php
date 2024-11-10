<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Models\User;

use Carbon\Carbon;
use Illuminate\Support\Facades\DB;
use App\Database\Smart;
use App\Service\Api;
use App\Support;

/**
 */
class Photo extends Api\User\UserValidator
{
	/**
	 */
	protected $validatorRules = [
        'media' => 'required'
    ];
	/**
	 */
	protected $media;
    /**
	 */
	public function validate(){
		if (!parent::validate()) {
			return false;
		}
		if (!$this->validateMedia()) {
			return false;
		}
		return true;
	}

	/**
     */
    public function validateMedia() {
        $file = $this->validatorData->get('media');
        $this->media = $this->getUser()->medias()
            ->where('media.media_status_id', Smart\MediaStatus::STATUS_TEMPORARY_ID)
            ->where('media.uid', $file)
            ->first();
        if (!$this->media) {
            return false;
        }
        return true;
    }

	/**
	 */
	public function upload() {
		$user = $this->getUser();

		$oldUserPhoto = $user->getUserPhoto();

		if ($oldUserPhoto!=null) {
			$oldUserPhoto->delete();
		}
		$photo = new Smart\UserPhoto();
		$photo->user_id = $user->id;
		$photo->media_uid = $this->media->uid;
		$photo->media_user_id = $this->media->user_id;
		$photo->created_user_id = $user->id;
		$photo->save();

		/** */
		$this->media->media_status_id = Smart\MediaStatus::STATUS_ACTIVE_ID;
		$this->media->updated_user_id = $user->id;
		$this->media->is_updated = 1;
		$this->media->save();	
	}
	/**
	 */
	public function response(){
		$user =  $this->getUser();
        $response['user'] = $user;
		$response['user']['username'] = $user->getUsername()->username;
		$response['user']['role'] = $user->getUserRole() != null ? $user->getUserRole()->getRole()->name : null;
		$response['user']['image'] = $user->getUserPhoto()  != null ? sprintf('https:%s%s', Api\Config::get('media.file'), $user->getUserPhoto()->getMedia()->file) : null;
		return $response;
	}
}