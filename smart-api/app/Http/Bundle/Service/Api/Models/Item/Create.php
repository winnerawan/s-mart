<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Models\Item;

use Carbon\Carbon;
use Illuminate\Http\Request;
use App\Database\Smart;
use App\Service\Api;
use App\Support;
use Illuminate\Support\Str;
/**
 */
class Create extends Api\User\UserValidator
{
    /**
	 */
	protected $validatorRules = [
        'name' => 'required|string',
        'category' => 'required|numeric',
        'sku' => 'required|string',
        'name' => 'required|string',
        'description' => 'nullable|string',
        'media' => 'nullable|string',
    ];

    protected $category;
    protected $media;

     /**
	 */
	public function validate(){
		if (!parent::validate()) {
			return false;
		}
        $this->category = Smart\Category::select()
            ->where('category.id', $this->validatorData->get('category'))
            ->first();
        if (!$this->category) {
            return false;
        }
        $this->validateMedia();
        return true;
    }

     /**
     */
    public function validateMedia() {
        $file = $this->validatorData->get('media');
        $this->media = $this->getUser()->medias()
            ->where('media.media_status_id', Smart\MediaStatus::STATUS_TEMPORARY_ID)
            ->where('media.id', $file)
            ->first();
        if (!$this->media) {
            return true;
        }
        return true;
    }

    /**
     */
    public function create() {
        return Smart\User::transaction(function(){
            $now = \Carbon\Carbon::now();
            // $uuid = Str::uuid();
            $item = new Smart\Item();
            // $item->id = $uuid;//Support\Uid::alpha(8);//;
            $item->name = $this->validatorData->get('name');
            $item->category_id = $this->category->id;
            $item->sku = $this->validatorData->get('sku');
            $item->description = $this->validatorData->get('description');
            $item->save();

            if ($this->media) {
				$itemMedia = new Smart\ItemMedia();
				$itemMedia->item_id = $item->id;
                $itemMedia->category_id = $this->category->id;
                $itemMedia->sku = $this->validatorData->get('sku');
				$itemMedia->media_id = $this->media->id;
				$itemMedia->media_user_id = $this->media->user_id;
				$itemMedia->created_user_id = $this->getUser()->id;
				$itemMedia->save();

				/** */
				$this->media->media_status_id = Smart\MediaStatus::STATUS_ACTIVE_ID;
				$this->media->updated_user_id = $this->getUser()->id;
				$this->media->is_updated = 1;
				$this->media->save();
			}
            
		});
    }

    

}