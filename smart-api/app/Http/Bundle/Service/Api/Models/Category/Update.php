<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Models\Category;

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
        'category' => 'required|numeric',
        'name' => 'nullable|string',
        'description' => 'nullable|string',
        'position' => 'nullable|numeric'
    ];

    protected $category;

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
        return true;
    }

    /**
     */
    public function update() {
        return Smart\User::transaction(function(){
            $this->category->name = $this->validatorData->get('name');
            $this->category->description = $this->validatorData->get('description');
            // $this->category->updated_user_id = $this->getUser()->id;
            // $this->category->is_updated = 1;
            $this->category->save();
		});
    }

    /**
     */
    public function updatePosition() {
        return Smart\User::transaction(function(){
            $this->category->position = $this->validatorData->get('position');
            // $this->category->updated_user_id = $this->getUser()->id;
            // $this->category->is_updated = 1;
            $this->category->save();
		});
    }
}