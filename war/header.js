(function (){
    "use strict";
    function createMenuItem(parent, label, link){
        var li = document.createElement("li");
        li.className = "yui3-menuitem";
        parent.appendChild(li);
        var a = document.createElement("a");
        li.appendChild(a);
        a.className = "yui3-menuitem-content";
        a.href = link;
        a.appendChild(document.createTextNode(label));
    }

    function buildSubMenu(parent, label, id){
        var li = document.createElement("li");
        parent.appendChild(li);
        var a = document.createElement("a");
        li.appendChild(a);
        a.className = "yui3-menu-label";
        a.href = "#"+id;
        a.appendChild(document.createTextNode(label));
        var subMenu = document.createElement("div");
        subMenu.className = "yui3-menu";
        subMenu.id = id;
        li.appendChild(subMenu);
        var subMenuContent = document.createElement("div");
        subMenuContent.className = "yui3-menu-content";
        subMenu.appendChild(subMenuContent);
        var ul = document.createElement("uv");
        subMenuContent.appendChild(ul);
        return ul;
    }

    function buildTopMenu(){
        var header = document.getElementsByTagName("header")[0];
        header.className = "yui3-g";
        var divU = document.createElement("div");
        divU.className = "yui3-u";
        divU.style.width = "100%";

        header.appendChild(divU);
        var topmenu = document.createElement("div");
        divU.appendChild(topmenu);
        topmenu.className = "yui3-menu yui3-menu-horizontal";
        topmenu.id = "topmenu";

        var menuContent = document.createElement("div");
        menuContent.className = "yui3-menu-content";
        topmenu.appendChild(menuContent);
        var ul = document.createElement("ul");
        menuContent.appendChild(ul);

        createMenuItem(ul, "KickJS", "http://www.kickjs.org/");
        var documentationSubMenu = buildSubMenu(ul,"Documentation","documentation");
        createMenuItem(documentationSubMenu, "API", "/api/index.html");
        createMenuItem(documentationSubMenu, "Tutorial", "/tutorial/index.html");
        var examplesSubMenu = buildSubMenu(ul,"Examples","examples");
        createMenuItem(examplesSubMenu, "Shader editor", "/example/shader_editor/shader_editor.html");
        createMenuItem(examplesSubMenu, "Video ascii art", "/example/video_ascii_art/Video_Ascii_Art.html");
        createMenuItem(ul, "Source", "https://github.com/mortennobel/KickJS");
        createMenuItem(ul, "Bugs", "https://github.com/mortennobel/KickJS/issues");
    }

    if (!window.YUI){
        console.log("YUI not loaded");
        return;
    }

    window.YUI().use('node-menunav', function (Y) {
        buildTopMenu();

        var menu = Y.one("#topmenu");

        menu.plug(Y.Plugin.NodeMenuNav);
    });
})();