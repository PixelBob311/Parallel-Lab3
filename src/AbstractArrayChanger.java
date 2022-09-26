public abstract class AbstractArrayChanger implements ChangerInterface {

    protected int iters;
    protected long timeElapsed = -1;

    protected String changerName = "название метода";
    protected CommonResource resource;

    public AbstractArrayChanger(CommonResource resource, int iters) {
        this.resource = resource;
        this.iters = iters;
    }

    public CommonResource getResource() {
        return this.resource;
    }

    public String getTimeElapsed() {
        return this.changerName + " : " + this.timeElapsed + "мс";
    }
}
