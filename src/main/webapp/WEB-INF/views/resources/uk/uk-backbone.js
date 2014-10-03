// Dependencias:
// Underscore, JQuery, PrimeUI (solo en DatatableUI, TreeUI)
// 2 tipos de UI: con modelo asociado o sin modelo asociado
// funcionamiento general:
// 1.- UI con modelo asociado: se espera un cambio de estado en el modelo (normalmente fetch y entonces renderiza x eventos)
// 2.- UI sin modelo asociado: se instanceia el gestor de la UI y entonces se renderiza
//
// Vinculando UIs: mediante 
//

var Bb = Backbone;

var Element = Bb.Model.extend({
    defaults: {
        url: "",
        events: {},
        ui: {}
    },
    initialize: function(){
        this.map();
        this.trigger('change:initialize');
    },
    map: function(){
        var fields = _.keys(this.attributes);
        for(var i=0; i<fields.length; i++){
            this[fields[i]] = this.get(fields[i]);
        };
        this.listenTo(this,'change',this.map);
        return this;
    },
    bind: function(model,events){
        if(this!==model && model!=undefined){
            this.model = model;
            if(events!=undefined){ _.extend(this.events,events) }
            for(var i=0; i<_.keys(this.events).length; i++){
                if(_.contains(_.functions(this),_.values(this.events)[i])){
                    this.listenTo(this.model,_.keys(this.events)[i],this[_.values(this.events)[i]]);
                }
            }
            this.trigger('change:bind');
            return this;
        }
    },
    send: function(fields,callback,object,url){
        var datum;
        if(!(typeof fields === "string")){
            if(fields!=undefined && object!=undefined){
                datum = JSON.stringify(object,fields);
            }else if(fields!=undefined && object==undefined){
                datum = JSON.stringify(this,fields);
            }else if(fields==undefined && object==undefined){
                datum = JSON.stringify(this);
            };
        }else{
            datum = fields;
        };
        var body = {
            url: url || this.url,
            type: 'POST',
            data: datum,
            dataType: 'json',
            contentType: 'application/json'
        };
        if(callback!=undefined){ _.extend(body,{success: callback}); };
        $.ajax(body);
        this.trigger('change:send');
        return this;
    }
});

var ElementUI = Element.extend({
    defaults: {
        view: "",
        model: {},
        options: {}
    },
    render: function(){},
    onClickUI: function(own,controls){
        _.each(controls,function(v,k){
            var el = $(own.view).find(v);
            _.each(el, function(e,ix){
                e.onclick =  function(){own.trigger('on:'+k.toLowerCase(),ix);};
            });
        });
        _.each(controls,function(v,k){own.listenTo(own,'on:'+k.toLowerCase(),own[k]);});
        this.trigger('change:onclickui');
        return this;
    }
});

var Mediator = Bb.Model.extend({
    defaults: {
        producer: {},
        consumer: {},
        events: {
            'change:producer': {callback: 'consumerFunction', inputs: []}
        }
    },
    initialize: function(){
        this.consumer = this.get('consumer');
        this.producer = this.get('producer');
        this.events   = this.get('events');
        this.listenTo(this,'change:addevents',this.bind);
        this.listenTo(this,'change:setevents',this.bind);
        this.listenTo(this,'change:setconsumer',this.bind);
        this.listenTo(this,'change:setproducer',this.bind);
        this.bind();
    },
    bind: function(){
        for (var i = 0; i < _.keys(this.events.length).length; i++) {
            consumer.listenTo(producer,_.keys(this.events.length)[i],
                consumer[_.values(events.length)[i].callback](_.values(events.length)[i].inputs)
            );
        };
        this.trigger('change:bind');
        return this;
    },
    unbind: function(){
        for (var i = 0; i < _.keys(this.events.length).length; i++) {
            consumer.stopListening(producer, _.keys(this.events.length)[i],
                consumer[_.values(events.length)[i].callback](_.values(events.length)[i].inputs)
            );
        };
        this.trigger('change:unbind');
        return this;
    },
    clone: function(){
        var m = new Mediator();
        m.consumer = this.get('consumer');
        m.producer = this.get('producer');
        m.events = this.get('events');
        m.bind();
        return m;
    },
    addEvents: function(newEvents){
        _.extend(this.events,newEvents);
        this.trigger('change:addevents');
        return this;
    },
    setEvents: function(newEvents){
        this.events = newEvents;
        this.trigger('change:setevents');
        return this;
    },
    setConsumer: function(newConsumer,isUnbind){
        if(isUnbind){ this.unbind(); };
        this.consumer = newConsumer;
        this.trigger('change:setconsumer');
        return this;
    },
    setProducer: function(newProducer,isUnbind){
        if(isUnbind){ this.unbind(); };
        this.producer = newProducer;
        this.trigger('change:setproducer');
        return this;
    },
    setPair: function(newConsumer,newProducer,isUnbind){
        if(isUnbind){ this.unbind(); };
        this.consumer = newConsumer;
        this.producer = newProducer;
        this.trigger('change:setpair');
        return this;
    }
});

var DatatableUI = ElementUI.extend({
    initialize: function(){
        this.map();
        this.options = _.extend({
            wildcard : "$",
            caption: 'DatatableUI',
            paginator: { rows: 10 },
            selectionMode: 'multiple'
        },this.options);
        this.combination = [];
        this.listenTo(this.model,'change:onmodelchange',this.render);
        this.listenTo(this.model,'change:select',this.combineSelectedFields);
        this.listenTo(this.model,'change:unselect',this.combineSelectedFields);
    },
    render : function(){
        if($(this.view).length>0 && this.model.columns!=undefined && this.model.datasource!=undefined){
            $(this.view).puidatatable({
                columns: this.model.columns,
                datasource: this.model.datasource,
                caption: this.options.caption,
                paginator: this.options.paginator,
                selectionMode: this.options.selectionMode
            });
        };
        this.onSelect();
        this.onUnselect();
        this.trigger('change:render')
        return this;
    },
    onSelect : function(callback){
        var view = this.view,
            own  = this;
        if($(view).length>0){
            $(view).puidatatable({ rowSelect: function(e,d){ 
                own.model.select($(view).puidatatable('getSelection'));
            } });
        };
        this.trigger('change:onselect')
        return this;
    },
    onUnselect : function(callback){
        var view = this.view,
            own  = this;
        if($(view).length>0){
            $(view).puidatatable({ rowUnselect: function(e,d){
                own.model.select($(view).puidatatable('getSelection'));
            } });
        };
        this.trigger('change:onunselect')
        return this;
    },
    combineFields : function(values,fields){
        var wc = this.options.wildcard;
        if(fields!=undefined){
            return _.keys(_.indexBy(values,fields)).length==1?_.keys(_.indexBy(values,fields))[0]:wc;
        }else{
            return [];
        };
        this.trigger('change:combinefields');
        return this;
    },
    combineSelectedFields: function(){
        var aux;
        this.combination = [];
        for(var i=0;i<this.model.columns.length;i++){
            aux = this.combineFields(this.model.selection,_.keys(this.model.selection[0])[i]);
            if(!_.isEmpty(aux)){ this.combination[i] = aux; }
        };
        this.trigger('change:combineselectedfields');
        return this;
    }
});

var Datatable = Element.extend({
    defaults: {
        url: "",
        selection: []
    },
    initialize: function(){
        this.url = this.get('url');
        this.columns    = this.get('columns');
        this.datasource = this.get('datasource');
        this.selection  = this.get('selection');
        this.listenTo(this,"change",this.onModelChange);
    },
    select: function(data){
        this.selection = data;
        this.trigger('change:select');
        return this;
    },
    unselect: function(data){
        this.selection = _.without(this.selection,data);
        this.trigger('change:unselect');
        return this;
    },
    onModelChange: function(){
        this.columns    = this.get('columns');
        this.datasource = this.get('datasource');
        this.trigger('change:onmodelchange');
        return this;
    }
});

var MessageUI = ElementUI.extend({
    defaults: {
        view: "",
        model: {},
        options: {
            closeIcon: '.close-icon'
        }
    },
    initialize: function(){
        this.map();
        this.closeIconClass = this.get('options').closeIcon;
        this.listenTo(this,'change:render',this.onClose);
        this.listenTo(this.model,'change',this.render);
        this.model.bind(this,{'notify:close':'accept'});
        this.trigger('change:initialize');
    },
    render: function(){
        if($(this.view).length>0){
            var ic = this.closeIconClass;
            if(!this.model.accepted){
                $(this.view).html("<div class=\"notifier\">"+this.model.content+"<div class=\""+ic.substring(1,ic.length)+"\"></div></div>");
            }else{
                $(this.view).remove();
            }
        }
        this.trigger('change:render');
        return this;
    },
    onClose: function(callback){
        var own = this,
            ic = this.closeIconClass,
            ex = $(this.view).find(ic).length>0;
        if( ex && callback==undefined ){
            $(this.view).find(ic)[0].onclick = function(){own.trigger('notify:close')};
        }else if(ex){ 
            callback(own); 
        };
        return this;
    }
});

var Message = Element.extend({
    defaults: {
        url: "",
        accepted: false,
        content: "<p></p>",
        events: {},
        ui: {}
    },
    initialize: function(){
        this.map();
    },
    accept: function(){        
        this.set('accepted',true);
        this.send(['url','accepted','content']);
        return this;
    }
});

var TreeUI = ElementUI.extend({
    initialize: function(){
        this.view = this.get('view');
        this.model = this.get('model');
        this.options = this.get('options');
        this.options = _.extend({},this.options);
        this.listenTo(this.model,'change:onmodelchange',this.render);
    },
    render: function(){
        if($(this.view).length>0){
            $(this.view).puitree({nodes: this.model.nodes});
        };
        return this;
    }
});

var Tree = Element.extend({
    defaults: {
        url: "",
        nodes: []
    },
    initialize: function(nodes){
        this.url = this.get('url');
        this.nodes = [];
        this.listenTo(this,"change",this.onModelChange);
    },
    onModelChange: function(){
        var own = this;
        _.each(_.keys(own.attributes),function(e){
            if(!isNaN(parseInt(e))){
                own.nodes.push(own.get(e));
            };
        });
        this.trigger('change:onmodelchange');
        return this;
    }
});

var FormUI = ElementUI.extend({
    defaults: {
        view: "",
        model: {},
        options: {}
    },
    initialize: function(){
        this.map();
        this.listenTo(this,'change',this.render);
        this.listenTo(this,'change:submit',this.submit);
    },
    render: function(){
        if($(this.view).length>0){ this.setFields(); };
        // los elementos .mark siven para dar una valoracion numerica que se muestra en #lbl-markid
        if($(this.view).find('.mark').length>0){
            _.each($(this.view).find('.mark'), function(e){
                $(e).on('change',function(){$('#lbl-'+$(e).attr('id')).html($(e).val())}); $(e).change();
            });
        };
        // submit x defecto dispara change:submit y no envia automaticamente
        if($(this.view).find("button[type='submit']").length>0){
            _.each($(this.view).find("button[type='submit']"), function(e){
                e.onclick = function(){this.trigger('change:submit'); return false;};
            });
        };
        this.trigger('change:render');
        return this;
    },
    getFields: function(){
        if($(this.view).length>0){
            for(var i=0; i<_.keys(this.model.fields).length; i++){
                if($(_.keys(this.model.fields)[i]).length>0){
                    this.model.set(_.values(this.model.fields)[i],$(this.view).find(_.keys(this.model.fields)[i]).val());
                }
            }
            this.trigger('change:getfields');
        };
        return this;
    },
    setFields: function(){
        if($(this.view).length>0){
            for(var i=0; i<_.keys(this.model.fields).length; i++){
                if($(_.keys(this.model.fields)[i]).length>0){
                    $(this.view).find(_.keys(this.model.fields)[i]).val(this.model[_.values(this.model.fields)[i]]);
                }
            }
            this.trigger('change:setfields');
        };
        return this;
    },
    getFormData: function(){
        return $(this.view).serializeObject();
    },
    submit: function(){
        this.getFields();
        this.model.send(this.getFormData());
    },
});

var Form = Element.extend({
    defaults: {
        url: "",    
        fields: {'#username':'username',
                 '#password':'password'}
    },
    initialize: function(){
        this.map();
        this.trigger('change:initialize');
    },
    initFields: function(){
        for(var i=0; i<_.keys(this.fields).length; i++){ this.fields[_.keys(this.fields)[i]] = ''; }
        this.trigger('change:initfields');
        return this;
    },
    seqFilling: function(values){
        for(var i=0; i<_.min([_.keys(this.fields).length,values.length]); i++){
            if(!_.isEmpty(values) && values[i]!=undefined){
                this.fields[_.keys(this.fields)[i]] = values[i];
            }
        };
        this.set('fields',this.fields);
        this.trigger('change:seqfilling');
        return this;
    }
});

var ListUI = ElementUI.extend({
    initialize: function(){
        this.map();
        this.listenTo(this,'change:getmodel',this.render);
    },
    render: function(){
        if ($(this.view).length>0) {
            $(this.view).html('<ul>'+this.populate(this.model.get('root'))+'</ul>');
        };
        this.trigger('change:render');
        return this;
    },
    populate: function(node){
        var text = "";
        for(var i = 0; i<node.length; i++){
            var li = (node[i].class!=undefined)?['<li class=\''+node[i].class+'\'>','</li>']:['<li>','</li>'];
            var ah = (node[i].url!=undefined)?['<a href=\''+node[i].url+'\'>','</a>']:['',''];
            if(node[i].children.length==0){
                text += ah[0]+li[0]+node[i].caption+li[1]+ah[1];
            }else{
                text += li[0]+ah[0]+node[i].caption+'<ul>'+this.populate(node[i].children)+'</ul>'+li[1]+ah[1];
            }
        }
        return text;
    },
    getModel: function(){
        var own = this;
        var m   = this.model;
        m.fetch().success( function(data){ m.set('root',data); m.map(); own.trigger('change:getmodel');});
        return this;
    }
});

var List = Element.extend({
    defaults: {
        url: '',
        root: {}
    }
});

var Node =  Element.extend({
    defaults: {
        'url' : '',
        'caption' : '',
        'class' : '',
        'children'   : []
    }
});

var TabsUI = ElementUI.extend({
    defaults: {
        view: "",
        model: {},
        options: { class: 'tabGroup', init: 0 }
    },
    initialize: function(){
        this.view = this.get('view');
        this.model = this.get('model');
        this.options = this.get('options');
        this.listenTo(this.model,'change:model',this.updateView);
    },
    render: function(){
        if($(this.view).length>0){
            $(this.view).html(this.template(this.model.panel,this.get('options')));
        };
        this.trigger('change:render');
        return this;
    },
    template: function(panel,options){
        var parent = function(tabs,content){
            if(panel!=undefined)
                return "<div id=\""+panel.id+"\"class=\""+options.class+"\">"+content+"</div>";
        };
        var header = function(i,tab,name){return "<input type=\"radio\" name=\""+name+"\" id=\"tab"+i+"\" class=\"tab"+i+"\">"+
                                            "<label for=\"tab"+i+"\">"+tab.title+"</label> ";};
        var container = function(i,tab){return "<div class=\"tab"+i+"\">"+tab.content+"</div>";};
        var tabs = _.filter(_.keys(panel), function(e){return e.match(/tab.+/);}),
            headers = "",    
            containers = "";
        for(var i = 0; i< tabs.length; i++){
            headers += header((this.options.init+i+1),panel[tabs[i]],panel.id);
            containers += container((this.options.init+i+1),panel[tabs[i]]);
        };
        return parent(panel,headers+containers);
    }
});
var Tabs = Element.extend({
    defaults: {
        url: "",
        events: {},
        panel: {}
    }
});

var IdiomsUI = ElementUI.extend({
    defaults: {
        'es':'#es_flag',
        'en':'#en_flag'
    },
    initialize: function(){
        _.each(this.attributes, function(v,k){ if($(v).length>0) $(v)[0].onclick = function(){ location.href=('./setlang?lang='+k); } });
        this.trigger('change:initialize');
    }
});

var PostUI = ElementUI.extend({
    // templating desactivado por el momento
    defaults: {
        view: '#last_comments',
        model: [{
            mark: 1.5,
            author: {
                url: '/user/john-doe',
                name: 'John Doe' 
            },
            date: {
                datetime: '1986-12-28',
                title: 'December 28th, 1986',
                content: '29/12/1986'
            },
            content: "<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque"
                     +" et tincidunt arcu. Sed pulvinar sodales urna et pharetra. Curabitur tincidunt"
                     +" massa ac nisi laoreet tincidunt. Nam quis suscipit tellus. Maecenas nec dictum"
                     +" erat. Nunc id nunc sapien. Pellentesque accumsan metus non tellus semper, ut"
                     +" commodo est sollicitudin. Praesent at erat laoreet, aliquet ipsum et, scelerisque"
                     +" purus. Sed laoreet sit amet tortor a tempor.</p>"
        }],
        options: {
            total_stars  : 5,               //#maximo de notas a dar    
            icon_fully   : "&#xe042;",      // codigo de icono de nota = 1
            icon_fifty   : "&#xe041;",      // codigo de icono de nota = 0.5
            icon_emmpty  : "&#xe040;",      // codigo de icono de nota = 0
            main_class   : ".post",
            stars_class  : ".stars",        // ID del elemento dentro de la que se incluiran las notas
            stars_attr   : "data-mark",     // Nombre del atributo que contendra el valor numerico de la nota
            full_class   : "full",          // Clase para el icono de nota = 1
            fifty_class  : "full",          // Clase para el icono de nota = 0.5
            empty_class  : "empty"          // Clase para el icono de nota = 0
        }
    },
    initialize: function(){
        this.map();
        this.trigger('change:initialize');
    },
    render: function(){
        var own = this;
        var post, stars, mark, fully, fifty, empty;
        //for (var i = 0; i < this.model.length; i++) {
        //    $(this.get('view')).append(this.template(i));
        //};
        post  = $(this.get('view')).find(this.options.main_class);
        if(post.length>0){
            stars = post.find(this.options.stars_class);
            if(stars.length>0){
                for (var i = 0; i < stars.length; i++) {            
                    mark  = $(stars[i]).attr(this.options.stars_attr);
                    fully = Math.floor(mark);
                    fifty = (Math.floor(mark)!=Math.round(mark))?1:0;
                    empty = this.options.total_stars-(fully+fifty);
                    own.qualify(stars[i], fully, fifty, empty);
                };
            };
        };
        return this;
    },
    qualify: function(stars,fully,fifty,empty){
        var innerHTML = "";
        for (var i=0; i<fully; i++) {
            innerHTML+=("<span class=\"full\">"+this.options.icon_fully+"<\span>");
        };
        for (var i=0; i<fifty; i++) {
            innerHTML+=("<span class=\"full\">"+this.options.icon_fifty+"<\span>");
        };
        for (var i=0; i<empty; i++) {
            innerHTML+=("<span class=\"empty\">"+this.options.icon_emmpty+"<\span>");
        };
        $(stars).html(innerHTML);
        return this;
    },
    template: function(i){
        mark    = this.model[i].mark;
        author  = this.model[i].author;
        date    = this.model[i].date;
        content = this.model[i].content;
        return "<article class='post'>"+
            "<header>"+
                "<div class='stars' data-mark='"+mark+"'></div>"+
                "<div class='authoring'>"+
                    "<address class='author'>By <a rel='author' href='"+author.url+"'>"+author.name+"</a></address> "+
                    "<time pubdate datetime='"+date.datetime+"' title='"+date.title+"'>"+date.content+"</time>"+
                "</div>"+
            "</header>"+
            "<div class='content'>"+content+"</div>"+
        "</article>";
    }
});

var TableUI = ElementUI.extend({
    defaults: {
        view: '#cart-body',
        header: {},
        model: {},
        options: {
            sortable: [1,2,3],
            removable: true,
        },
        controls: {
            row: {
                'removeBy':'.x-icon'
            },
            column: {
                'flip': '.sort-icon',
                'sortBy': '.alpha-icon'
            }
        }
    },
    initialize: function(){
        this.map();
        this.trigger('change:initialize');
        this.listenTo(this,'on:removeby',this.removeBy);
        this.listenTo(this,'change:removeby',this.render);
        this.listenTo(this,'change:removeby',this.model.fetch);
        this.listenTo(this,'on:add',this.add);
        this.listenTo(this,'change:add',this.render);
        this.listenTo(this,'change:add',this.model.fetch);
        this.listenTo(this,'on:sortby',this.sortBy);
        this.listenTo(this,'change:sortby',this.render);
        this.listenTo(this,'on:flip',this.flip);
        this.listenTo(this,'change:flip',this.render);
    },
    template: function(sort){
        var own = this;
        if(sort==undefined){ sort = "<div class='sorting-options'><span class='sort-icon'></span><span class='alpha-icon'></span></div>"; }
        var header = function(object){ 
            var c = ""; 
            _.each(object,function(v,k){
                if( _.contains(own.options.sortable,parseInt(k)) ){ 
                    c+="<th class='sortable'>"+v+sort+"</th>";
                } else {
                    c+="<th>"+v+"</th>";
                };
            })
            return "<tr>"+ c +"</tr>";
        };
        var row    = function(object){ 
            var c = ""; 
            _.each(object,function(v,k){return c+="<td>"+v+"</td>";});
            return "<tr>"+ c +"</tr>";
        };
        var c="";
        _.each(this.model.toJSON(),function(v){return c+=row(v);});
        return header(this.header)+c;
    },
    render: function(){
        if($(this.view).length>0){ $(this.view).html(this.template()); };
        if (this.options.removable){
            var own = this;
            _.each(this.controls.row,function(v,k){
                var el = $(own.view).find(v);
                _.each(el, function(e,ix){
                    e.onclick =  function(){own.trigger('on:'+k.toLowerCase(),ix);};
                });
            });
        };
        if (this.options.sortable.length>=1){
            var own = this;
            var f   = $(own.view).find('.sortable');
            _.each(this.controls.column,function(v,k){
                var el = $(own.view).find(v);
                _.each(el, function(e,ix){                    
                    var ix = f.index($(e).parent().parent());
                    e.onclick =  function(){own.trigger('on:'+k.toLowerCase(),ix+1);};
                });
            });
        };
        this.trigger('change:render');
        return this;
    },
    add: function(node){
        this.model.push(node);
        this.trigger('change:add');
        return this;
    },
    removeBy: function(row){
        this.model.remove(this.model.models[row]);
        $(this.view).find('tr')[row].remove();
        this.trigger('change:removeby');
        return this;
    },
    sortBy: function(col){
        if(_.contains(this.options.sortable,parseInt(col))){
            this.model = new Bb.Collection(this.model.sortBy(col));
            this.trigger('change:sortby');
        }else{
            throw new Error('sortBy :: undefined field');
        };
        return this;
    },
    flip: function(){
        this.model = new Bb.Collection(this.model.models.reverse());
        this.trigger('change:sortby');
        return this;
    },
    getField: function(col){
        var field = [];
        _.each(this.model.models,function(v){field.push(v.get(col))});
        this.trigger('change:getfield');
        return field;
    },
    getRow: function(row){
        this.trigger('change:getrow');
        return this.model.models[row];
    }
});

var StripeCart = Element.extend({
    defaults: {
        url: 'cart/checkout',
        urlUpdate: 'cart/update',
        urlConfirm: 'cart/confirm',
        cart: new Bb.Collection([
            {'_id':'Item-1', 'quant':'2', 'price': 400, 'x':"<span class='x-icon'></span>"},
            {'_id':'Item-2', 'quant':'1', 'price': 600, 'x':"<span class='x-icon'></span>"}]),
        transaction: {
            name: 'Checkout',
            description: 'My Books',
            amount: 2000
        },
        config: {
            key: 'pk_test_LSfws1uyFEXFAh9HgaaMYF04',
            //image: '/square-image.png'            
        }
    },
    initialize: function(){
        this.map();
        this.handler = StripeCheckout.configure(_.extend(this.config,{
            token: function(token, args) {
                location.href = this.urlConfirm;
            }
        }));
        this.listenTo(this,'cart:send-transaction',this.sendTransaction);
    },
    checkout: function(){        
        this.send({'cart':this.cart}, function(){ this.trigger('cart:send-transaction'); },this,this.url);
        this.trigger('cart:send-transaction');
        this.trigger('change:checkout');
        return this;
    },
    remove: function(){        
        this.cart = new Bb.Collection();
        this.send({'cart':this.cart},undefined,this,this.urlUpdate);
        this.trigger('change:remove');
        return this;
    },
    removeBy: function(ix){        
        this.cart.remove(this.cart[ix]);
        this.send({'cart':this.cart},undefined,this,this.urlUpdate);
        this.trigger('change:removeby');
        return this;
    },
    sendTransaction: function(){
        //this.transaction.amount = 
        this.handler.open(this.transaction);
        this.trigger('change:settransaction');
        return this;
    }
});

var CartUI = ElementUI.extend({
    defaults: {
        view: '#cart-controller',
        scrollView: '#cart',
        model: new StripeCart(),
        table: new TableUI({
            header: {"_id":'Items', "quant":'Q', "price":'Price: €', "x":''}
        }),
        controls: {
            'checkout': '.checkout',
            'remove': '.remove',
        },
    },
    initialize: function(){
        this.map();
        this.table.model = this.model.cart;
        this.trigger('change:initialize');
        this.listenTo(this,'change:checkout',this.update);
        this.listenTo(this,'change:remove',this.update);
        this.listenTo(this,'change:update',this.render);
        this.listenTo(this,'change:render',this.checkStatus);
        this.model.listenTo(this.table,'change:removeby',this.model.removeBy);
    },
    render: function(){
        this.onClickUI(this,this.controls);
        this.table.render();
        this.trigger('change:render');
        return this;
    },
    update: function(){
        this.table.model = this.model.cart;
        this.trigger('change:update');
        return this;
    },
    checkout: function(){
        this.model.checkout();
        this.trigger('change:checkout');
        return this;
    },
    remove: function(){
        this.model.remove();
        this.trigger('change:remove');
        return this;
    },
    checkStatus: function(){
        if (_.isEmpty(this.table.model)){
            $(this.view).find(this.controls['checkout']).removeClass('cart-empty-icon');
            $(this.view).find(this.controls['checkout']).addClass('cart-full-icon');
        } else {
            $(this.view).find(this.controls['checkout']).addClass('cart-empty-icon');
            $(this.view).find(this.controls['checkout']).removeClass('cart-full-icon');
        }
        this.trigger('change:checkstatus');
        return this;
    },
});

function Factory(){
    this.type  = 'Factory';
    this.build = function(){};
};

function TogglerFactory(){

    /**
        Inner toggler class, add or remove classes on "#button-i" click, IE>9
        solo depende de underscore.js ( _.each() )
        { class: "any-class", elements: { "#button-1": { toggle: "#div-1", remove: ["#div-2", "#div-3"] } } }
    */
    // TODO: aceptar elements: {"button": ["e1","e2"]} y elements: {"button": {toggle: ["e1","e2"]} }
    function TogglerObject(toggler){  

        var toggleField = 'toggle';
        var removeField = 'remove';

        function initialize(toggler){
            _.each(toggler.elements, function(destiny_id,button_id){
                var target, button = document.querySelector(button_id);
                if (button){
                    if (destiny_id instanceof Object){
                        target = (target = document.querySelector(destiny_id[toggleField]))!==null ? target.classList : null;
                        _.each(destiny_id[removeField], function(e){
                            var $e = ($e = document.querySelector(e))!==null ? $e.classList : null;
                            button.addEventListener('click', function(){
                                if($e && !$e.contains(toggler.class)) $e.add(toggler.class);
                            }, false);
                        });
                    }else if(typeof destiny_id == "string"){
                        target = (target = document.querySelector(destiny_id))!==null ? target.classList : null; 
                    };
                    button.addEventListener('click', function(){
                        if(target) target.contains(toggler.class) ? target.remove(toggler.class) : target.add(toggler.class);
                    }, false);
                    button.onclick = function(){return false;}
                };
            });
        };

        return initialize(toggler);
    };

    return _.extend(new Factory(),{
        type: 'TogglerFactory',
        build: function(toggler){
            new TogglerObject(toggler);
            return this;
        }
    });
};

function Pusher(model){

    var pattern = [
        {
            button: '.add-to-cart-form',
            form: '.add-to-cart-form',
            url: 'addItem'
         }
    ]

    function initialize(data){
        _.each(data, function(e){
            var button = document.querySelector(e.button);
            var serial = new Serializer();
            if(button) {
                var dataFm = serial.Form2JSON(document.querySelector(e.form));
                button.onclick = function(){  
                    $.fn.postJSON( $.fn.path("addItem"), dataFm, function(data){
                        cc.model.set('cart', cc.model.get('cart').add(data));
                        cc.model.map();
                        cc.map().render();
                    } ); 
                    return false; 
                };
            };
        });            
    };

    return initialize(model || pattern);
};

function Serializer(){

    function initialize(){};

    /** Receives plain HTML form and convert to plain JS object 
    */
    this.Form2JSON = function(form){
        var result = {};
        _.each( form.elements, function(e){
            var o={}; o[e.name] = e.value;
            if(e.name) { _.extend(this.f,o); };
        }, { f: result } );
        return result;
    }

    return initialize();
};