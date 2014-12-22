package papermm.engine;

import com.jme3.input.ChaseCamera;
import com.jme3.input.InputManager;
import com.jme3.math.FastMath;
import com.jme3.renderer.Camera;

public class ChaseCamera2 extends ChaseCamera {
    public ChaseCamera2(Camera cam, InputManager inputManager) {
        super(cam, inputManager);

        Float rotation = (FastMath.PI / 3) - .1f;
        setMaxVerticalRotation(rotation);
        setMinVerticalRotation(rotation);
        setRotation(FastMath.PI);
        setDefaultDistance(24.8f);
        setDefaultVerticalRotation(rotation);
    }

    public void setRotation(Float rot) {
        rotating = true;
        targetRotation = rot;
    }

    public Float getRotation() {
        return rotation;
    }

    protected void zoomCamera(float value) {}
}
