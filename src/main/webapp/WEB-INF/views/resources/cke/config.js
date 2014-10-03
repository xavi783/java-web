/**
 * @license Copyright (c) 2003-2014, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.html or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	config.language = 'es';
	config.uiColor = '#82a609';
	config.resize_enabled = false;
	config.height = '26em';
	config.toolbarGroups = [
	    { name: 'document',    groups: [ 'mode', 'document', 'doctools' ] },
	    { name: 'clipboard',   groups: [ 'clipboard', 'undo' ] },
	    { name: 'tools' },
	    { name: 'editing',     groups: [ 'find', 'selection', 'spellchecker' ] },
	    { name: 'colors' },
	    { name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
	    { name: 'links' },
	    { name: 'insert' },
	    { name: 'styles' },
	    { name: 'paragraph',   groups: [ 'list', 'indent', 'blocks', 'align' ] },
	];
};
