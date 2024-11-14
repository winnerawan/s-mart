<?php
/**
 */
namespace App\Database\Smart;

/**
 */
class ItemMedia extends AbstractModel
{
    /**
     */
    protected $table = 'item_media';

    /**
     */
    protected $primaryKeys = [
        'item_id' => 'item_id',
        'sku' => 'sku',
        'category_id' => 'category_id',
        'media_id' => 'media_id'
    ];

    /**
     */
    protected $media;

    /**
     */
    public function media(){
        return Media::where([
            'media.id' => $this->media_id
        ]);
    }

    /**
     */
    public function getMedia(){
        if ($this->media==null) {
            $this->media = $this->media()
                // ->orderBy('created_datetime', 'DESC')
                ->first();
        }
        return $this->media;
    }
}