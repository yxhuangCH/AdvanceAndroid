package com.yxhuang.airhockey.objects;

import android.opengl.GLES20;

import com.yxhuang.airhockey.util.Geometry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yxhuang
 * Date: 2017/12/18
 * Description:
 */

public class ObjectBuilder {

    private static final int FLOATS_PER_VERTEX = 3;
    private final float[] vertexData;
    private int offset = 0;
    private final List<DrawCommand> mDrawList = new ArrayList<>();

    private ObjectBuilder(int sizeInvertices){
        vertexData = new float[sizeInvertices * FLOATS_PER_VERTEX];
    }

    // 计算圆柱顶部数量的方法
    private static int sizeOfCircleInvertices(int numPoints){
        return 1 + (numPoints + 1);
    }

    // 计算圆柱侧面定点数量
    private static int sizeOfOpenCylinderInvertices(int numPoints){
        return (numPoints + 1) * 2;
    }

    private void appendCircle(Geometry.Circle circle, int numPoints){
        final int starVertex = offset / FLOATS_PER_VERTEX;
        final int numVertices = sizeOfCircleInvertices(numPoints);

        // Center point of fan
        vertexData[offset++] = circle.center.x;
        vertexData[offset++] = circle.center.y;
        vertexData[offset++] = circle.center.z;

        for (int i = 0; i <= numPoints; i++){
            float angleInRadius = ((float)i / (float) numPoints) * ((float)Math.PI * 2f);

            vertexData[offset++] = circle.center.x + circle.radius * (float) Math.cos(angleInRadius);
            vertexData[offset++] = circle.center.y;
            vertexData[offset++] = circle.center.z + circle.radius * (float) Math.sin(angleInRadius);
        }
        mDrawList.add(new DrawCommand() {
            @Override
            public void draw() {
                GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, starVertex, numVertices);
            }
        });
    }

    private void appendOpenCylinder(Geometry.Cylinder cylinder, int numPoints){
        final int startVertex = offset / FLOATS_PER_VERTEX;
        final int numVertices = sizeOfOpenCylinderInvertices(numPoints);
        final float yStart = cylinder.center.y - (cylinder.height / 2);
        final float yEnd = cylinder.center.y + (cylinder.height / 2f);

        for (int i = 0; i <= numPoints; i++){
            float angleInRadius = ((float)i / (float)numPoints) * (float)(Math.PI * 2f);
            float xPosition = cylinder.center.x + cylinder.radius * (float) Math.cos(angleInRadius);
            float zPosition = cylinder.center.z + cylinder.radius * (float) Math.sin(angleInRadius);

            vertexData[offset++] = xPosition;
            vertexData[offset++] = yStart;
            vertexData[offset++] = zPosition;

            vertexData[offset++] = xPosition;
            vertexData[offset++] = yEnd;
            vertexData[offset++] = zPosition;
        }

        mDrawList.add(new DrawCommand() {
            @Override
            public void draw() {
                GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, startVertex, numVertices);
            }
        });
    }

    private GenerateData build(){
        return new GenerateData(vertexData, mDrawList);
    }



    // 创建冰球
    static GenerateData createPuck(Geometry.Cylinder puck, int numPoints){
        int size = sizeOfCircleInvertices(numPoints) + sizeOfOpenCylinderInvertices(numPoints);

        ObjectBuilder builder = new ObjectBuilder(size);

        Geometry.Circle puckTop = new Geometry.Circle(
                puck.center.translateY(puck.height / 2f),
                puck.radius);
        builder.appendCircle(puckTop, numPoints);
        builder.appendOpenCylinder(puck, numPoints);
        return builder.build();
     }



     // 创建木锥
    static GenerateData createMallet(Geometry.Point center, float radius, float height, int numPoints){
        int size = sizeOfCircleInvertices(numPoints) * 2 + sizeOfOpenCylinderInvertices(numPoints) * 2;
        ObjectBuilder builder = new ObjectBuilder(size);

        // First, generate the mallet base
        float baseHeight = height * 0.25f;

        Geometry.Circle baseCircle = new Geometry.Circle(center.translateY(-baseHeight), radius);
        Geometry.Cylinder baseCylinder = new Geometry.Cylinder(
                baseCircle.center.translateY(-baseHeight / 2f),
                radius,baseHeight);

        builder.appendCircle(baseCircle, numPoints);
        builder.appendOpenCylinder(baseCylinder, numPoints);

        // 生成手柄
        float handleHeight = height * 0.75f;
        float handleRadius = radius / 3f;
        Geometry.Circle handleCircle = new Geometry.Circle(center.translateY(height * 0.5f), handleRadius);
        Geometry.Cylinder handleCylinder = new Geometry.Cylinder(
                    handleCircle.center.translateY(-handleHeight / 2f),
                    handleRadius, handleHeight);
        builder.appendCircle(handleCircle, numPoints);
        builder.appendOpenCylinder(handleCylinder, numPoints);

        return builder.build();
    }



    static class GenerateData {
        final float[] vertexData;
        final List<DrawCommand> drawList;

        public GenerateData(float[] vertexData, List<DrawCommand> drawList) {
            this.vertexData = vertexData;
            this.drawList = drawList;
        }
    }

    static interface DrawCommand{
        void draw();
    }
}
