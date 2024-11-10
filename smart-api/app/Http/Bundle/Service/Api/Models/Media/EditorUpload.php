<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Models\Media;

use App\Database\Smart;
use App\Service\Api;
use App\Support;

/**
 */
class EditorUpload extends Api\User\UserValidator
{
	/**
	 */
	protected $validatorRules = [
        'media' => 'required|file|max:210000', /* 210MB */
	];
	
	/**
	 */
    protected $file;
	protected $media;
	protected $path;
	protected $mimetype;

	/**
	 */
	public function getFileSize(){
		return $this->file->getSize();
	}

	/**
	 */
	public function getFileName(){
		return $this->file->getClientOriginalName();
	}

	/**
	 */
	public function getFileExtension(){
		return $this->file->getClientOriginalExtension();
    }


	/**
	 */
	public function validate(){
		if (!parent::validate()) {
			return false;
		}
        $this->file = $this->validatorData->get('media');
        $this->mimetype = Smart\MediaMimetype::where('mimetype', $this->file->getMimeType())->first();
		if (!$this->mimetype) {
			$this->validator->errors()->add('file', sprintf('The file type not supported: %s', $this->file->getMimeType()));
			return false;
        }
		$this->path = Smart\MediaPath::select()
			->where('media_path_status_id', Smart\MediaPathStatus::STATUS_ACTIVE_ID)
			->inRandomOrder()
			->first();
		return true;
	}
	
	/**
	 */
	public function upload(){
		Smart\User::transaction(function(){
            $this->createMedia();
		});
    }
    
    /**
     */
    protected function createMedia() {
        /**/
        $user = $this->getUser();

		$this->media = new Smart\Media();
		$this->media->user_id = $user->id;
		$this->media->uid = Support\Uid::number();
		$this->media->media_path_id = $this->path->id;
		$this->media->media_status_id = Smart\MediaStatus::STATUS_ACTIVE_ID;
		$this->media->media_mimetype_id = $this->mimetype->id;
		$this->media->name = $this->getFileName();
		$this->media->size = $this->getFileSize();
		$this->media->file = vsprintf('%s.%s',[static::encodeName($this->media), $this->file->guessClientExtension()]);
		$this->media->created_user_id = $user->id;
		$this->media->save();
		/**/
		$directory = Api\Config::get('media.storage') . $this->path->name;
		$this->file->move($directory, $this->media->file);
    }


	/**
	 */
	public function response() {
		$response['url'] = sprintf('https:%s%s', Api\Config::get('media.file'), $this->media->file);
		$response['media_uid'] = $this->media->uid;
		return $response;
	}
	
	/**
	 */
	public static function encodeName($media){
		return str_replace(['+','/','='], ['-','_','$'], base64_encode(json_encode([
			(string)$media->user_id,
			(string)$media->uid
		])));
	}
	
	/**
	 */
	public static function decodeName($value){
		$value = json_decode(base64_decode(str_replace(['-','_','$'], ['+','/','='], $value), true), true);
		if (is_array($value) && count($value) == 2) {
			return Smart\Media::where(['user_id' => $value[0], 'uid' => $value[1]])->first();
		}
	}
}