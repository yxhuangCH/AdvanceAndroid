笔记：
    相关链接
        https://developer.android.com/training/graphics/opengl/index.html
        https://developer.android.com/guide/topics/graphics/opengl.html
        http://www.learnopengles.com/
    
### 主要类
#### GLSurfaceView
. GLSurfaceView 默认是 PixelFormat.RGB_888 格式；
. GLSurfaceView 的生命周期，需要在 Activity 的 onPause() 和 onResume() 方法中进行相应的调用；
 
#### GLSurfaceView.Renderer
主要三个方法
. onSurfaceCreated() 
  &ensp; Called once to set up the view's OpenGL ES environment;<br>
  . onDrawFrame()
  &nbsp; Called for each redraw of the view;<br>
  . onSurfaceChanged()
  &nbsp; Called if the geometry of the view changes, for example when the device's screen orientation changes.
        
### Training Part
#### 1.概念：
. A GLSurfaceView is a view container for graphics drawn with OpenGL.
. GLSurfaceView.Renderer controls what is drawn within that view.

#### 2. OpenGL ES 系统相对于 Android 平面的坐标


#### 3. Drawing Shapes
. shape 在 GLSurfaceView.Render#onSurfaceCreated() 方法中初始化；
. 绘制 Shape 需要以下条件：
    &nbsp; . Vertex Shaper : 定点信息
        &nbsp; OpenGL ES graphics code for rendering the vertices of a shape;
    &nbsp; . Fragment Shader 渲染信息
        &ensp; OpenGL ES code for rendering the face of a shape with colors or textures;
        . Program
            &nbsp; An OpenGlL ES object the contains the shaders you want to use for drawing one or more shapes.
            
#### 4. Applying Projection and Camera Views
. Projection
    &nbsp; This transformation adjusts the coordinates of drawn objects based on the width and height of the GLSurfaceView where they are displayed.
. Camera View
    &nbsp; This transformation adjusts the coordinates of drawn objects based on a virtual camera position.

    
        
    
        

