<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Models\Purchase;

use Carbon\Carbon;
use Illuminate\Http\Request;
use App\Database\Smart;
use App\Service\Api;
use App\Support;

/**
 */
class Delete extends Api\User\UserValidator
{
    /**
	 */
	protected $validatorRules = [
        'category' => 'required|numeric'
    ];

    protected $category;

     /**
	 */
	public function validate() {
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
    public function delete() {
        return Smart\User::transaction(function(){
            if ($this->category->hasItems()) {
                return;
            }
            //todo: revert item_stock
            $this->category->delete();
		});
    }

    

}