<?php
/*-------------------------------------------------------------------------
 * /media
 *-------------------------------------------------------------------------
 */
$router->get('/data', [
	'as' => '/data',
	'uses' => 'MediaController@data'
]);

$router->post('/upload', [
	'as' => '/upload',
	'uses' => 'MediaController@upload'
]);


$router->post('/editorUpload', [
	'as' => '/editorUpload',
	'uses' => 'MediaController@editorUpload'
]);

$router->post('/remove', [
	'as' => '/remove',
	'uses' => 'MediaController@remove'
]);