(function (){
    "use strict";
    function createMenuItem(parent, label, link, title){
        var li = document.createElement("li");
        li.className = "yui3-menuitem";
        parent.appendChild(li);
        var a = document.createElement("a");
        li.appendChild(a);
        a.className = "yui3-menuitem-content";
        a.href = link;
        a.appendChild(document.createTextNode(label));
        if (title){
            a.title = title;
        }
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
        createMenuItem(documentationSubMenu, "Tutorial - 0 - Installation", "/tutorial/part0.html");
        createMenuItem(documentationSubMenu, "Tutorial - 1 - The component based scenegraph", "/tutorial/part1.html");
        createMenuItem(documentationSubMenu, "Tutorial - 2 - Materials and shaders", "/tutorial/part2.html");
        createMenuItem(documentationSubMenu, "Tutorial - 3 - Shader Editor", "/tutorial/part3.html");
        createMenuItem(documentationSubMenu, "Tutorial - 4 - Scripting and time", "/tutorial/part4.html");
        createMenuItem(documentationSubMenu, "Tutorial - 5 - Key Input", "/tutorial/part5.html");
        var examplesSubMenu = buildSubMenu(ul,"Examples","examples");
        createMenuItem(examplesSubMenu, "Shader editor",    "/example/shader_editor/shader_editor.html");
        createMenuItem(examplesSubMenu, "Video ascii art",  "/example/video_ascii_art/Video_Ascii_Art.html");
        createMenuItem(examplesSubMenu, "WebCam ascii art", "/example/webcam_ascii_art/Webcam_Ascii_Art.html");
        createMenuItem(examplesSubMenu, "Model viewer",     "/example/model_viewer/model_viewer.html");
        createMenuItem(examplesSubMenu, "Cloth simulation", "/example/cloth_simulation/cloth_simulation.html");
        createMenuItem(examplesSubMenu, "Snake",            "/example/snake/snake.html");
        createMenuItem(examplesSubMenu, "Chess",            "/example/chess/chess.html");
        createMenuItem(ul, "Source", "https://github.com/mortennobel/KickJS", "Find the full source on GitHub");
        createMenuItem(ul, "Bugs", "https://github.com/mortennobel/KickJS/issues", "Report bugs on GitHub");
        createMenuItem(ul, "Ask a question", "http://stackoverflow.com/questions/ask?tags=webgl&title=KickJS", "Questions? We will be happy to help you on StackOverflow.com.");
    }

    if (!window.YUI){
        console.log("YUI not loaded"); return;
    }

    window.YUI().use('node-menunav', function (Y) {
        buildTopMenu();

        var menu = Y.one("#topmenu");

        menu.plug(Y.Plugin.NodeMenuNav);
    });
})();