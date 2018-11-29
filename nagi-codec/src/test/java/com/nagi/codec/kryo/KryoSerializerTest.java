package com.nagi.codec.kryo;

import com.nagi.codec.bean.Dog;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/** 
* KryoSerializer Tester. 
* 
* @author <Authors name> 
* @since <pre>ʮһ�� 29, 2018</pre> 
* @version 1.0 
*/ 
public class KryoSerializerTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: serialize(Object object, ByteBuf out) 
* 
*/ 
@Test
public void testSerialize() throws Exception {
    Dog dog = new Dog();
    dog.setAge(10);
    dog.setName("haha");
    Map<String,Object> map = new HashMap<String, Object>();
    map.put("ki","op");
    dog.setAttributes(map);

    ByteBuf buffer = Unpooled.buffer();
    KryoSerializer.serialize(dog,buffer);

    /*byte[] bytes = new byte[buffer.readableBytes()];
    buffer.readBytes(bytes);

    String dcdc = new String(bytes);*/

    Object deserialize = KryoSerializer.deserialize(buffer);


    System.out.println(deserialize.toString());

} 

/** 
* 
* Method: deserialize(ByteBuf out) 
* 
*/ 
@Test
public void testDeserialize() throws Exception { 


} 


} 
