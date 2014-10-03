
var PRODUCTION_STATE=true;

$(function(){
    
    // Global
    var nc = new MessageUI({ view: '#notify-cookies', model: new Message({url: 'cookiesAccepted'}) });
    var v  = new IdiomsUI().render();

    // Edition
    var ul = new DatatableUI({ view: '#user-table', model: new Datatable({url: 'usersData'}) });
    var rl = new DatatableUI({ view: '#role-table', model: new Datatable({url: 'rolesData'}) });
    var tr = new TreeUI({ view: '#tree', model: new Tree({url: 'blog/categories'}) });

    // UIs sin modelo asociado
    var p  = new PostUI().render();
    var cc = new CartUI().render();
    var cmform = new FormUI({view:'#comment-form', model: new Form({url:'comment-form', fields: {'#mark-range':'','#comment':'',}})}).render();

    if(PRODUCTION_STATE && $(ul.view).length>0){ ul.model.fetch(); }
    if(PRODUCTION_STATE && $(rl.view).length>0){ rl.model.fetch(); }
    if(PRODUCTION_STATE && $(tr.view).length>0){ tr.model.fetch(); }
    if(PRODUCTION_STATE && $(nc.view).length>0){ nc.model.fetch(); }

    if($("#search-content").length>0){ $("#search-content").tinyscrollbar({axis: 'y'}); }
    if($(cc.scrollView).length>0){ $(cc.scrollView).tinyscrollbar({axis: 'y'}); }

    // Simple toggling:
    simpleToggler = {
        class: "hidden",
        elements: {
            "#button-search": { 
                toggle: "#advanced-search-form",
                remove: ["#signin-form", "#login-form"]
            },
            "#show-signin-form": { 
                toggle: "#signin-form", 
                remove: ["#advanced-search-form", "#login-form"]
            },
            "#show-login-form": { 
                toggle: "#login-form", 
                remove: ["#advanced-search-form", "#signin-form"]
            }
        }
    };
    new TogglerFactory().build(simpleToggler);   

    if ($("#ckeditor").length>0){
        var editor = CKEDITOR.replace('ckeditor');
        editor.on('save',function(evt){
            var data = this.getData();
            success = function(r){if(r){$('#saved')[0].checked=true}else{alert("Error saving article")}} 
            $("#ckeditor").giveTo(data,"edition/editor/save",success);
            return false; // para evitar que CKEditor recarge pagina al guardar
        });
        $("#ckeditor").find('#cke_1_contents').css("min-heigt:26em;")
    };
});
