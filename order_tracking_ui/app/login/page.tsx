"use client";

import type { FormProps } from 'antd';
import { Button, Form, Input } from 'antd';
import { useAuth } from "@/hooks/useAuth";
import { useRouter } from "next/navigation";
import { useEffect } from "react";
import Container from "@/components/ui/Container";
import Card from '@/components/ui/Card';

export default function LoginPage() {
  const { signIn, isAuthenticated, loading } = useAuth();
  const router = useRouter();

  type FieldType = {
    email?: string;
    password?: string;
  };

  useEffect(() => {
    if (!loading && isAuthenticated) {
      router.replace("/");
    }
  }, [isAuthenticated, loading, router]);

  const onFinish: FormProps<FieldType>["onFinish"] = async (values) => {
    if (!values.email || !values.password) {
      return;
    }

    await signIn({
      email: values.email,
      password: values.password,
    });

    router.replace("/");
  };

  const onFinishFailed: FormProps<FieldType>["onFinishFailed"] = (errorInfo) => {
    console.log('Failed:', errorInfo);
  };

  if (loading) {
    return null;
  }

  return (
      <Container>
        <Card>
        <h1 className="text-1xl font-bold mb-4">Página de Login</h1>

        <Form
          name="login"
          className="flex flex-col gap-4"
          initialValues={{ remember: true }}
          onFinish={onFinish}
          onFinishFailed={onFinishFailed}
        >
          <Form.Item
            name="email"
            rules={[{ required: true, message: 'Preencha o seu email' }]}
          >
            <Input placeholder="Email" />
          </Form.Item>

          <Form.Item
            name="password"
            rules={[{ required: true, message: 'Preencha a sua senha' }]}
          >
            <Input.Password placeholder="Senha" />
          </Form.Item>

          <Form.Item>
            <Button type="primary" htmlType="submit" className="w-full">
              Entrar
            </Button>
          </Form.Item>
        </Form>
        </Card>
      </Container>
  );
}