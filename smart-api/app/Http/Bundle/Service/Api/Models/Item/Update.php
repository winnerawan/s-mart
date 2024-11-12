<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Models\Item;

use Carbon\Carbon;
use Illuminate\Http\Request;
use App\Database\Smart;
use App\Service\Api;
use App\Support;

/**
 */
class Update extends Api\User\UserValidator
{
    /**
	 */
	protected $validatorRules = [
        'item' => 'required|string',
        'category' => 'nullable|numeric',
        'name' => 'nullable|string',
        'description' => 'nullable|string',
        'position' => 'nullable|numeric',
        'sku' => 'nullable|string'
    ];

    protected $category;
    protected $item;
    protected $media;

     /**
	 */
	public function validate(){
		if (!parent::validate()) {
			return false;
		}
        
        $this->item = Smart\Item::select()
            ->where([
                'item.id' => $this->validatorData->get('item'),
            ])
            ->first();
        if (!$this->item) {
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
            ->where('media.uid', $file)
            ->first();
        if (!$this->media) {
            return true;
        }
        return true;
    }

    /**
     */
    public function update() {
        return Smart\User::transaction(function(){
            $this->item->name = $this->validatorData->get('name');
            $this->item->category_id = $this->validatorData->get('category');
            $this->item->sku = $this->validatorData->get('sku');
            $this->item->description = $this->validatorData->get('description');
            // $this->category->updated_user_id = $this->getUser()->id;
            // $this->category->is_updated = 1;
            $this->item->save();

            if ($this->media) {
				$itemMedia = new Smart\ItemMedia();
				$itemMedia->item_id = $this->item->id;
                $itemMedia->category_id = $this->validatorData->get('category');
                $itemMedia->sku = $this->validatorData->get('sku');
				$itemMedia->media_uid = $this->media->uid;
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