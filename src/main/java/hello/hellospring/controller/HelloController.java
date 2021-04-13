package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model){
        model.addAttribute("data","hello!!");
        return "hello";
//        hello.html 에 값을 넘겨주라고 함.
    }

    @GetMapping("hello-mvc")
    public String hellomvc(@RequestParam(value = "name") String name, Model model){
                            //value = "name"(외부에서 받아온)값을 String name("key")에 담아서 모델에 넘겨줌
        model.addAttribute("name", name);
        return "hello-template"; //model에 있는 name값이 hello-template.html로 전달된다.
    }

    @GetMapping("hello-string")
    @ResponseBody //http의 body 부분에 return "hello" + name; 내용을 직접 넣어주겠다
    public String helloString(@RequestParam("name") String name){
        return "hello" + name; // "hello 외부에서 입력한 값"
    }

//    json방식
    @GetMapping("hello-api")
    @ResponseBody // <- 만나면 viewResolver가 아니라 HttpMessageConverter로 넘어간다.
    public Hello helloApi(@RequestParam("name") String name){
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
        //자료타입이 아니라 객체를 전달하게 되면 MappingJackson2HttpMessageConverter가 동작하게되고 
        // json{"key":"value"}형식으로 반환한다.
        // 자료형타입일 경우(ex. String, int ...) StringHttpMessageConverter가 동작하게 된다.
        // MappingJackson2 : json 변환 라이브러리
    }

    static class Hello{
        private String name;

        public String getName(){

            return name;
        }

        public void setName(String name){

            this.name=name;
        }

    }
}
