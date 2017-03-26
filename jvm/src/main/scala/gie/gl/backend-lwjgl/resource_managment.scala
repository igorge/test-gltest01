package gie.gl.resource

import slogging.LoggerHolder


trait DisposableOnceResource extends AutoCloseable {
    protected def release():Unit
    def dispose(): Unit

    final def close(): Unit = dispose()
}


abstract class DisposableOnceAbstract[T](p_resource:T) extends DisposableOnceResource {

    private var resource:Option[T] = Some(p_resource)

    @inline final def apply() = get
    @inline final def get = resource.get

    protected override def release():Unit

    override def dispose(): Unit ={
        if (resource.isDefined){
            release()
            resource = None
        }
    }

}






class ResourceReference(holder: AnyRef,
                        value: DisposableOnceResource,
                        queue: java.lang.ref.ReferenceQueue[AnyRef]) extends java.lang.ref.PhantomReference[AnyRef](holder, queue)
{
    def dispose(): Unit ={
        value.dispose()
    }
}





trait ResourceContext extends AutoCloseable { this: LoggerHolder =>

    private lazy val m_homeTread = Thread.currentThread.getId

    protected val phantomsQueue = new java.lang.ref.ReferenceQueue[AnyRef]()
    protected val resources = new collection.mutable.HashSet[ResourceReference]

    def queue = phantomsQueue

    @inline private def impl_checkHome(){
        if(m_homeTread != Thread.currentThread.getId) throw new Exception(s"Not at home, ${m_homeTread} != ${Thread.currentThread.getId}")
    }

    def registerResourceReference[T](holder: AnyRef, disposableOnce: DisposableOnceAbstract[T]):ResourceReference = {
        impl_checkHome()

        val phantomRef = new ResourceReference(holder, disposableOnce, queue)
        resources.add(phantomRef)

        phantomRef
    }


    def gcTick(){
        impl_checkHome()

        val tmp = phantomsQueue.poll().asInstanceOf[ResourceReference]
        if (tmp ne null ) {
            val r = resources.remove(tmp)
            assert(r)
            try{ tmp.dispose() } catch {
                case ex:Throwable => logger.error(s"Exception while freeing resources: ${ex}")
            }
            logger.debug(s"gcTick(): released resource: ${tmp}")
        }
    }

    def gcAllOnQueue(){
        logger.info("gcAllOnQueue()")
        
        impl_checkHome()

        var tmp = phantomsQueue.poll().asInstanceOf[ResourceReference]

        while (tmp ne null ) {
            val r = resources.remove(tmp)
            assert(r)
            try{ tmp.dispose() } catch {
                case ex:Throwable => logger.error(s"Exception while freeing GL resources: ${ex}")
            }
            logger.debug("gcTick(): released gdx resource")
            tmp = phantomsQueue.poll().asInstanceOf[ResourceReference]
        }
    }


    def dispose(): Unit ={
        impl_checkHome()

        logger.debug(s"${resources.size} GL resource(s) allocated, freeing...")

        resources.foreach{ resourceRef=>
            try{ resourceRef.dispose() } catch {
                case ex:Throwable => logger.error(s"Exception while freeing gdx resources: ${ex}")
            }
        }
        logger.debug("gcForceClearAll()")
    }

    def close(): Unit = {
        dispose()
    }


}
