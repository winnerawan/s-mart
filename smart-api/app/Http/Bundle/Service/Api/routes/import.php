<?php
/*-------------------------------------------------------------------------
 * /export
 *-------------------------------------------------------------------------
 */

$router->post('/upload', [
	'as' => '/upload',
	'uses' => 'ImportController@upload'
]);

$router->get('/data', [
	'as' => '/data',
	'uses' => 'ImportController@data'
]);

$router->post('/process', [
	'as' => '/process',
	'uses' => 'ImportController@process'
]);

$router->post('/delete', [
	'as' => '/delete',
	'uses' => 'ImportController@delete'
]);

