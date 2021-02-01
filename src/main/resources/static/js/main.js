function getIndex(list, id) {
    for (let i = 0; i < list.length; i++) {
        if (list[i].id === id) {
            return i;
        }
    }
    return -1;
}

var messageApi = new Vue.resource('/message{/id}');

Vue.component('message-row',{
    props: ['message', 'editMethod', 'messages'],
    template:'<div>' +
        '<i>({{ message.id }})</i> {{ message.text }}' +
        '<span><input type="button" value="Edit" v-on:click="edit"/></span>' +
        '<span><input type="button" value="X" v-on:click="del"/></span>' +
        '</div>',
    methods: {
        edit: function () {
            this.editMethod(this.message);
        },
        del: function () {
            messageApi.remove({id: this.message.id}).then(res => {
                if (res.ok) {
                    this.messages.splice(this.messages.indexOf(this.message), 1);
                }
            })
        }
    }
});

Vue.component('message-form', {
    props: ['messages', 'messageAttr'],
    data : function () {
        return {
            text: '',
            id: ''
        }
    },
    template:
        '<div>' +
        '<input type="text" placeholder="Write message" v-model="text"/>' +
        '<input type="button" value="Save" v-on:click="save"/>' +
        '</div>',
    watch : {
        messageAttr: function (newVal, oldVal) {
            this.text = newVal.text;
            this.id = newVal.id;
        }
    },
    methods: {
        save: function () {
            var message = {text: this.text};
            if (this.id) {
                messageApi.update({id: this.id}, message).then(res =>
                    res.json().then(data => {
                        var index = getIndex(this.messages, data.id)
                        this.messages.splice(index, 1, data)
                        this.text = '';
                        this.id = '';
                    }));
            } else {
                messageApi.save({}, message).then(res =>
                    res.json().then(data => {
                        this.messages.push(data);
                        this.text = '';
                    }));
            }
        }
    }

});

Vue.component('messages-list', {
    props: ['messages'],
    data: function () {
        return  {
            message : null
        };
    },
    template:
        '<div>' +
        '<message-form :messages = "messages" :messageAttr = "message"/>' +
        '<message-row  :message = "message" :messages = "messages" :editMethod = "editMethod" v-for="message in messages" v-bind:key="message.id"/>' +
        '</div>',
    created: function () {
        messageApi.get().then(res => res.json()
                .then(data => data.forEach(message => this.messages.push(message))))
    },
    methods: {
        editMethod: function (message) {
            this.message = message;
        }
    }
});

var app = new Vue({
    el: '#app',
    template: '<messages-list :messages = "messages"/>',
    data: {
        messages: []
    }
});