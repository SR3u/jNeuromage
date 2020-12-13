package org.marasm.neuromage.math.jocl;

import org.jocl.CL;
import org.jocl.Pointer;
import org.jocl.Sizeof;
import org.jocl.cl_command_queue;
import org.jocl.cl_context;
import org.jocl.cl_context_properties;
import org.jocl.cl_device_id;
import org.jocl.cl_kernel;
import org.jocl.cl_platform_id;
import org.jocl.cl_program;
import org.marasm.neuromage.math.Vector;
import org.marasm.neuromage.math.VectorMath;

import static org.jocl.CL.CL_CONTEXT_DEVICES;
import static org.jocl.CL.CL_CONTEXT_PLATFORM;
import static org.jocl.CL.CL_DEVICE_TYPE_CPU;
import static org.jocl.CL.CL_DEVICE_TYPE_GPU;
import static org.jocl.CL.CL_QUEUE_OUT_OF_ORDER_EXEC_MODE_ENABLE;
import static org.jocl.CL.CL_QUEUE_PROFILING_ENABLE;
import static org.jocl.CL.clBuildProgram;
import static org.jocl.CL.clCreateCommandQueue;
import static org.jocl.CL.clCreateContextFromType;
import static org.jocl.CL.clCreateKernel;
import static org.jocl.CL.clCreateProgramWithSource;
import static org.jocl.CL.clGetContextInfo;
import static org.jocl.CL.clGetPlatformIDs;

public class JoclVectorMath implements VectorMath {

    private static final String programSource =
            "__kernel void test(" +
                    "     __global const double *a," +
                    "     __global const double *b, " +
                    "     __global double *c," +
                    "     int stride)" +
                    "{" +
                    "    int gid = get_global_id(0);" +
                    "    for (int i=0; i<stride; i++)" +
                    "    {" +
                    "        c[gid*stride+i]=a[gid*stride+i]+b[gid*stride+i];" +
                    "    }" +
                    "}";
    private final cl_kernel addKernel;


    public JoclVectorMath() {
        // Obtain the platform IDs and initialize the context properties
        System.out.println("Creating context...");
        cl_platform_id platforms[] = new cl_platform_id[1];
        clGetPlatformIDs(platforms.length, platforms, null);
        cl_context_properties contextProperties = new cl_context_properties();
        contextProperties.addProperty(CL_CONTEXT_PLATFORM, platforms[0]);

        // Try to create an OpenCL context on a GPU device
        cl_context context = clCreateContextFromType(
                contextProperties, CL_DEVICE_TYPE_GPU, null, null, null);
        if (context == null) {
            // If no context for a GPU device could be created,
            // try to create one for a CPU device.
            System.out.println("Unable to create a GPU context, using CPU...");
            context = clCreateContextFromType(
                    contextProperties, CL_DEVICE_TYPE_CPU, null, null, null);

            if (context == null) {
                throw new IllegalStateException("Unable to create a context");
            }
        }

        CL.setExceptionsEnabled(true);

        // Get the list of GPU devices associated with context
        System.out.println("Initializing device...");
        long numBytes[] = new long[1];
        clGetContextInfo(context, CL_CONTEXT_DEVICES, 0, null, numBytes);
        int numDevices = (int) numBytes[0] / Sizeof.cl_device_id;
        cl_device_id devices[] = new cl_device_id[numDevices];
        clGetContextInfo(context, CL_CONTEXT_DEVICES, numBytes[0],
                Pointer.to(devices), null);

        // Create a command-queue
        System.out.println("Creating command queue...");
        long properties = 0;
        properties |= CL_QUEUE_PROFILING_ENABLE;
        properties |= CL_QUEUE_OUT_OF_ORDER_EXEC_MODE_ENABLE;
        cl_command_queue commandQueue =
                clCreateCommandQueue(context, devices[0], properties, null);

        // Create the program
        System.out.println("Creating program...");
        cl_program program = clCreateProgramWithSource(context,
                1, new String[]{programSource}, null, null);

        // Build the program
        System.out.println("Building program...");
        clBuildProgram(program, 0, null, null, null, null);

        // Create the kernel
        System.out.println("Creating kernel...");
        addKernel = clCreateKernel(program, "add", null);


    }

    @Override
    public JoclVector vector(double... array) {
        return new JoclVector(array);
    }

    @Override
    public JoclVector convert(Vector v) {
        if (v instanceof JoclVector) {
            return (JoclVector) v;
        }
        return vector(v.calculate().data());
    }

    @Override
    public Vector add(Vector a, Vector b) {
        JoclVector A = convert(a);
        JoclVector B = convert(b);
        // Set the arguments for the kernel
        int i = 0;
        JoclVector c = new JoclVector(A.size());
        A.setKernelArgument(addKernel, i++);
        B.setKernelArgument(addKernel, i++);
        c.setKernelArgument(addKernel, i++);
        return null;
    }

    @Override
    public Vector sub(Vector a, Vector b) {
        return null;
    }

    @Override
    public Vector add(Vector a, double b) {
        return null;
    }

    @Override
    public Vector sub(Vector a, double b) {
        return null;
    }

    @Override
    public Vector sub(double a, Vector b) {
        return null;
    }

    @Override
    public Vector mul(Vector a, Vector b) {
        return null;
    }

    @Override
    public Vector mul(Vector a, double b) {
        return null;
    }

    @Override
    public Vector div(Vector a, Vector b) {
        return null;
    }

    @Override
    public Vector div(double a, Vector b) {
        return null;
    }

    @Override
    public Vector div(Vector a, double b) {
        return null;
    }

    @Override
    public Vector exp(Vector a) {
        return null;
    }

    @Override
    public double sum(Vector a) {
        return 0;
    }

}
