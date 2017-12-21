package com.yxhuang.airhockey.objects;

import com.yxhuang.airhockey.data.VertexArray;
import com.yxhuang.airhockey.programs.ColorShaderProgram;
import com.yxhuang.airhockey.util.Geometry;

import java.util.List;

/**
 * Created by yxhuang
 * Date: 2017/12/19
 * Description:
 */

public class Puck {
    private static final int POSITION_COMPONENT_COUNT = 3;

    public final float mRadius, mHeight;

    private final VertexArray mVertexArray;
    private final List<ObjectBuilder.DrawCommand> mDrawList;

    public Puck(float radius, float height, int numPointsAroundPuck){
        ObjectBuilder.GenerateData data = ObjectBuilder.createPuck(
                new Geometry.Cylinder(new Geometry.Point(0f, 0f, 0f), radius, height),
                numPointsAroundPuck);
        mRadius = radius;
        mHeight = height;

        mVertexArray = new VertexArray(data.vertexData);
        mDrawList = data.drawList;
    }

    // 把顶点数据绑定到着色器
    public void bindData(ColorShaderProgram colorProgram){
        mVertexArray.setVertexAttribPointer(
                0,
                colorProgram.getPositionLocation(),
                POSITION_COMPONENT_COUNT,
                0);
    }

    public void draw(){
        for (ObjectBuilder.DrawCommand drawCommand : mDrawList){
            drawCommand.draw();
        }
    }
}
