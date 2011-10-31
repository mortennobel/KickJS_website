YAHOO.env.classMap = {"KICK.mesh.MeshFactory": "KICK", "KICK.core.KeyInput": "KICK", "KICK.core.Engine": "KICK", "KICK.scene.ComponentChangedListener": "KICK", "KICK.core.Config": "KICK", "KICK.core.Time": "KICK", "KICK.mesh.MeshData": "KICK", "KICK.math.mat4": "KICK", "KICK.scene.Camera": "KICK", "KICK.math.mat3": "KICK", "KICK.material.GLSLConstants": "KICK", "KICK.mesh.Mesh": "KICK", "KICK.renderer.Renderer": "KICK", "KICK.scene.MeshRenderer": "KICK", "KICK.scene.Light": "KICK", "KICK.importer.ColladaImporter": "KICK", "KICK.math.vec4": "KICK", "KICK.scene.Transform": "KICK", "KICK.math.quat4": "KICK", "KICK.renderer.NullRenderer": "KICK", "KICK.math.vec3": "KICK", "KICK.core.Util": "KICK", "KICK.scene.Scene": "KICK", "KICK.scene.GameObject": "KICK", "KICK.texture.Texture": "KICK", "KICK.renderer.ForwardRenderer": "KICK", "KICK.scene.SceneLights": "KICK", "KICK.material.Material": "KICK", "KICK.core.Constants": "KICK", "KICK.material.Shader": "KICK", "KICK.scene.Component": "KICK"};

YAHOO.env.resolveClass = function (className) {
    var a=className.split('.'), ns=YAHOO.env.classMap;

    for (var i=0; i<a.length; i=i+1) {
        if (ns[a[i]]) {
            ns = ns[a[i]];
        } else {
            return null;
        }
    }

    return ns;
};
